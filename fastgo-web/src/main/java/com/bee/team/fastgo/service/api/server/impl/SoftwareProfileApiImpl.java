package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.common.ScriptTypeConstant;
import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.exception.sever.ScriptException;
import com.bee.team.fastgo.model.ServerDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.service.api.server.dto.req.ReqCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.dto.req.ReqExecScriptDTO;
import com.bee.team.fastgo.service.api.server.dto.res.ResCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.ScriptApi;
import com.bee.team.fastgo.service.api.server.SoftwareProfileApi;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * @author jgz
 * @date 2020/7/25
 * @desc 软件环境api实现
 **/
@Service
public class SoftwareProfileApiImpl implements SoftwareProfileApi {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ServerBo serverBo;

    @Autowired
    private ServerSoftwareProfileBo serverSoftwareProfileBo;

    @Autowired
    private ScriptApi scriptApi;

    @Override
    public ResCreateSoftwareDTO createSoftwareEnvironment(ReqCreateSoftwareDTO reqCreateSoftwareDTO) {
        checkParam(reqCreateSoftwareDTO);

        // 1.查询是否存在该配置?
        ServerSoftwareProfileDo ssp = serverSoftwareProfileBo.getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(reqCreateSoftwareDTO.getIp(),
                reqCreateSoftwareDTO.getSoftwareName(),
                reqCreateSoftwareDTO.getVersion());
        if(ssp != null){
            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
            resCreateSoftwareDTO.setSoftwareConfig(ssp.getSoftwareConfig());
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
        reqExecScriptDTO.setType(ScriptTypeConstant.INSTALL);
        // TODO 将查询id与项目code放置阻塞队列中,当构建完成后,去调用项目,修改其环境配置状态
        String selectId = scriptApi.execScript(reqExecScriptDTO);

        // 4.将该软件的信息保存到数据库
        ServerSoftwareProfileDo serverSoftwareProfileDo = new ServerSoftwareProfileDo();
        serverSoftwareProfileDo.setServerIp(reqCreateSoftwareDTO.getIp());
        serverSoftwareProfileDo.setSoftwareCode(reqCreateSoftwareDTO.getSoftwareName());
        serverSoftwareProfileDo.setSoftwareName(reqCreateSoftwareDTO.getSoftwareName() + "-" + reqCreateSoftwareDTO.getVersion());
        // TODO 软件环境元配置从软件资源库获取
        serverSoftwareProfileBo.saveServerSoftwareProfile(serverSoftwareProfileDo);
        // TODO 5.返回配置
        ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
        resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE0);
        // TODO 设置元配置
        //resCreateSoftwareDTO.setSoftwareConfig();
        return resCreateSoftwareDTO;
    }


    private void checkParam(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        if(StringUtils.isEmpty(reqCreateSoftwareDTO.getProjectCode())){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"传入的项目code为空");
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

}
