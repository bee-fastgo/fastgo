package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.common.ScriptTypeConstant;
import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.exception.sever.ScriptException;
import com.bee.team.fastgo.hander.JobHandler;
import com.bee.team.fastgo.hander.JobPush;
import com.bee.team.fastgo.hander.JobPushImpl;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.model.ServerDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.service.api.server.dto.req.ReqCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.dto.req.ReqExecScriptDTO;
import com.bee.team.fastgo.service.api.server.dto.req.ReqSoftwareInstallScriptExecResultDTO;
import com.bee.team.fastgo.service.api.server.dto.res.ResCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.ScriptApi;
import com.bee.team.fastgo.service.api.server.SoftwareProfileApi;
import com.bee.team.fastgo.service.project.ProjectBo;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.bee.team.fastgo.vo.project.req.UpdateProjectStatusVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.LocalCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * @author jgz
 * @date 2020/7/25
 * @desc 软件环境api实现
 **/
@Service
public class SoftwareProfileApiImpl implements SoftwareProfileApi, JobPush {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ServerBo serverBo;

    @Autowired
    private ProjectBo projectBo;

    @Autowired
    private ServerSoftwareProfileBo serverSoftwareProfileBo;

    @Autowired
    private ScriptApi scriptApi;

    private static CopyOnWriteArrayList<SoftwareInstallInfo> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    @Override
    public ResCreateSoftwareDTO createSoftwareEnvironment(ReqCreateSoftwareDTO reqCreateSoftwareDTO) {
        checkParam(reqCreateSoftwareDTO);

        // 1.查询是否存在该配置?
        ServerSoftwareProfileDo ssp = serverSoftwareProfileBo.getServerSoftwareProfileBySoftwareCode(reqCreateSoftwareDTO.getSoftwareCode());
        if(ssp != null){
            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
            // TODO 设置元配置
            resCreateSoftwareDTO.setSoftwareConfig("{\"port\":\"3306\",\"user\":\"root\",\"password\":\"123456\"}");
//            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
//            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
//            resCreateSoftwareDTO.setSoftwareConfig(ssp.getSoftwareConfig());
            return resCreateSoftwareDTO;
        }

        // 2.该服务器是否处于托管中?
        ServerDo serverDoByIp = serverBo.getServerDoByIp(reqCreateSoftwareDTO.getIp());
        if(serverDoByIp == null || CommonConstant.CODE1.equals(serverDoByIp.getServerStatus())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"服务器不处于托管中");
        }

        // 3.为该服务器安装软件,并拿到脚本执行的key
        ReqExecScriptDTO reqExecScriptDTO = baseSupport.objectCopy(reqCreateSoftwareDTO, ReqExecScriptDTO.class);
        // TODO 调用软件资源库获取下载地址
        reqExecScriptDTO.setSoftwareDownloadUrl("http://172.22.5.73/software");
        reqExecScriptDTO.setType(ScriptTypeConstant.INSTALL);
        String selectId = scriptApi.execScript(reqExecScriptDTO);
        JobHandler.jobMap.put(selectId, this);
        // 保存至缓存
        SoftwareInstallInfo softwareInstallInfo = new SoftwareInstallInfo();
        softwareInstallInfo.setSelectId(selectId);
        softwareInstallInfo.setSoftwareCode(reqCreateSoftwareDTO.getSoftwareCode());
        softwareInstallInfo.setSoftwareName(reqCreateSoftwareDTO.getSoftwareName());
        copyOnWriteArrayList.add(softwareInstallInfo);

        // 4.将该软件的信息保存到数据库
        ServerSoftwareProfileDo serverSoftwareProfileDo = new ServerSoftwareProfileDo();
        serverSoftwareProfileDo.setServerIp(reqCreateSoftwareDTO.getIp());
        serverSoftwareProfileDo.setSoftwareCode(reqCreateSoftwareDTO.getSoftwareCode());
        serverSoftwareProfileDo.setSoftwareName(reqCreateSoftwareDTO.getSoftwareName());
        serverSoftwareProfileDo.setVersion(reqCreateSoftwareDTO.getVersion());
        // TODO 软件环境元配置从软件资源库获取
        serverSoftwareProfileDo.setSoftwareConfig("{\"port\":\"3306\",\"user\":\"root\",\"password\":\"123456\"}");
        serverSoftwareProfileBo.saveServerSoftwareProfile(serverSoftwareProfileDo);

        // 5.返回配置
        ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
        resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE0);
        // TODO 设置元配置
        resCreateSoftwareDTO.setSoftwareConfig("{\"port\":\"3306\",\"user\":\"root\",\"password\":\"123456\"}");
        return resCreateSoftwareDTO;
    }


    /**
     * 任务执行结果回调
      * @param handleCallbackParam
     * @return
     * @author jgz
     * @date 2020/7/27
     * @desc
     */
    @Override
    public void receiveMessage(HandleCallbackParam handleCallbackParam) {
        Optional<SoftwareInstallInfo> first = copyOnWriteArrayList.stream().filter(softwareInstallInfo -> softwareInstallInfo.getSelectId().equals(String.valueOf(handleCallbackParam.getLogId()))).findFirst();
        if(first.isPresent()){
            //执行成功
            if(handleCallbackParam.getExecuteResult().getCode() == ReturnT.SUCCESS_CODE){
                //移除id对应记录
                copyOnWriteArrayList.removeIf(softwareInstallInfo -> softwareInstallInfo.getSelectId().equals(String.valueOf(handleCallbackParam.getLogId())));
                //如果移除后列表中没有任何一项的SoftwareCode等于被移除的项的SoftwareCode
                //即被移除的是最后一个
                if(copyOnWriteArrayList.stream().noneMatch(softwareInstallInfo -> softwareInstallInfo.getSoftwareCode().equals(first.get().getSoftwareCode()))){
                    //  通知项目模块环境创建完成
                    UpdateProjectStatusVo updateProjectStatusVo = new UpdateProjectStatusVo();
                    updateProjectStatusVo.setCode(first.get().getSoftwareCode());
                    updateProjectStatusVo.setType(Integer.valueOf(CommonConstant.CODE2));
                    projectBo.updateProjectStatus(updateProjectStatusVo);
                }
            }
            else {
                // TODO 通知项目模块环境创建失败 保留项(暂不实现)
                //移除列表中所有该项目相关的东西
                copyOnWriteArrayList.removeIf(softwareInstallInfo -> softwareInstallInfo.getSoftwareCode().equals(String.valueOf(first.get().getSoftwareCode())));
            }
        }
    }


    private void checkParam(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        if(StringUtils.isEmpty(reqCreateSoftwareDTO.getSoftwareCode())){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"传入的软件code为空");
        }
        if(StringUtils.isEmpty(reqCreateSoftwareDTO.getIp())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"服务器IP为空");
        }
        if(StringUtils.isEmpty(reqCreateSoftwareDTO.getSoftwareName())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件名为空");
        }
        if(Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).noneMatch(s -> s.equals(reqCreateSoftwareDTO.getSoftwareName()))) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "不支持的软件类型");
        }
        if(StringUtils.isEmpty(reqCreateSoftwareDTO.getVersion())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件版本号为空");
        }
    }

     static class SoftwareInstallInfo {

        private String selectId;

        private String softwareName;

        private String softwareCode;

         public String getSelectId() {
             return selectId;
         }

         public void setSelectId(String selectId) {
             this.selectId = selectId;
         }

         public String getSoftwareName() {
             return softwareName;
         }

         public void setSoftwareName(String softwareName) {
             this.softwareName = softwareName;
         }

         public String getSoftwareCode() {
             return softwareCode;
         }

         public void setSoftwareCode(String softwareCode) {
             this.softwareCode = softwareCode;
         }
     }

}
