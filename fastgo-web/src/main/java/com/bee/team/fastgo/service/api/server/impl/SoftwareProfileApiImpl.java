package com.bee.team.fastgo.service.api.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.common.ScriptTypeConstant;
import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.exception.sever.ScriptException;
import com.bee.team.fastgo.hander.JobHandler;
import com.bee.team.fastgo.hander.JobPush;
import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.model.ServerDo;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.service.api.server.ScriptApi;
import com.bee.team.fastgo.service.api.server.SoftwareProfileApi;
import com.bee.team.fastgo.service.api.server.dto.req.ReqCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.dto.req.ReqExecInstallScriptDTO;
import com.bee.team.fastgo.service.api.server.dto.res.ResCreateSoftwareDTO;
import com.bee.team.fastgo.service.project.ProjectBo;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerScriptBo;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.project.req.UpdateProjectStatusVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private ServerBo serverBo;

    @Autowired
    private ProjectBo projectBo;

    @Autowired
    private ServerSourceBo serverSourceBo;

    @Autowired
    private ServerScriptBo serverScriptBo;

    @Autowired
    private ServerSoftwareProfileBo serverSoftwareProfileBo;


    private static CopyOnWriteArrayList<SoftwareInstallInfo> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    @Override
    public ResCreateSoftwareDTO createSoftwareEnvironment(ReqCreateSoftwareDTO reqCreateSoftwareDTO) {
        //参数校验
        checkParam(reqCreateSoftwareDTO);

        if (SoftwareEnum.MYSQL.name().toLowerCase().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installMysql(reqCreateSoftwareDTO);
        }
        else if (SoftwareEnum.REDIS.name().toLowerCase().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installRedis(reqCreateSoftwareDTO);
        }
        else if (SoftwareEnum.KAFKA.name().toLowerCase().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installKafka(reqCreateSoftwareDTO);
        }
        else if (SoftwareEnum.MONGODB.name().toLowerCase().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installMongodb(reqCreateSoftwareDTO);
        }
        else if (SoftwareEnum.CASSANDRA.name().toLowerCase().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installCassandra(reqCreateSoftwareDTO);
        }else if (SoftwareEnum.ELASTICSEARCH.name().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installElasticsearch(reqCreateSoftwareDTO);
        }
        else if (SoftwareEnum.FLUME.name().toLowerCase().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installFlume(reqCreateSoftwareDTO);
        }
        else if (SoftwareEnum.ZOOKEEPER.name().toLowerCase().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installZookeeper(reqCreateSoftwareDTO);
        }
        else if (SoftwareEnum.DOCKER.name().toLowerCase().equals(reqCreateSoftwareDTO.getSoftwareName())){
            return installDocker(reqCreateSoftwareDTO);
        }
        else {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"暂不支持该软件");
        }
    }

    /**
     * 执行安装脚本
      * @param script 脚本
     * @param param 脚本参数
     * @param config 软件配置
     * @param reqCreateSoftwareDTO 基础信息
     * @return {@link ResCreateSoftwareDTO}
     * @author jgz
     * @date 2020/7/31
     */
    private ResCreateSoftwareDTO execInstallScript(String script,String param,String config,ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        // 执行脚本
        String selectId = SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, script, param, -1, reqCreateSoftwareDTO.getIp());
        JobHandler.jobMap.put(selectId, this);

        // 保存至缓存
        SoftwareInstallInfo softwareInstallInfo = new SoftwareInstallInfo();
        softwareInstallInfo.setSelectId(selectId);
        softwareInstallInfo.setProfileCode(reqCreateSoftwareDTO.getProfileCode());
        softwareInstallInfo.setSoftwareName(reqCreateSoftwareDTO.getSoftwareName());
        copyOnWriteArrayList.add(softwareInstallInfo);

        // 4.将该软件的信息保存到数据库
        ServerSoftwareProfileDo serverSoftwareProfileDo = new ServerSoftwareProfileDo();
        serverSoftwareProfileDo.setServerIp(reqCreateSoftwareDTO.getIp());
        serverSoftwareProfileDo.setSoftwareCode(RandomUtil.randomAll(16));
        serverSoftwareProfileDo.setSoftwareName(reqCreateSoftwareDTO.getSoftwareName());
        serverSoftwareProfileDo.setVersion(reqCreateSoftwareDTO.getVersion());
        serverSoftwareProfileDo.setSoftwareConfig(config);
        serverSoftwareProfileBo.saveServerSoftwareProfile(serverSoftwareProfileDo);

        // 5.返回配置
        ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
        resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE0);
        resCreateSoftwareDTO.setSoftwareCode(serverSoftwareProfileDo.getSoftwareCode());
        resCreateSoftwareDTO.setSoftwareConfig(config);
        return resCreateSoftwareDTO;
    }

    /**
     * 安装mysql
      * @param reqCreateSoftwareDTO
     * @return {@link ResCreateSoftwareDTO}
     * @author jgz
     * @date 2020/7/31
     * @desc
     */
    private ResCreateSoftwareDTO installMysql(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        Map<String, String> config = reqCreateSoftwareDTO.getConfig();
        if(StringUtils.isEmpty(config.get("dataSourceName"))){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"创建失败,未指定数据库名");
        }
        // 通过软件名+版本+ip+端口+数据库名 查询是否存在该mysql配置?
        ServerSoftwareProfileDo ssp = serverSoftwareProfileBo.getMysqlProfileBySoftwareNameAndVersionIpAndPortAndDataSourceName(
                reqCreateSoftwareDTO.getSoftwareName(),
                reqCreateSoftwareDTO.getVersion(),
                reqCreateSoftwareDTO.getIp(),
                StringUtils.isEmpty(config.get("port")) ? "3306" : config.get("port"),
                config.get("dataSourceName"));
        //存在直接返回
        if(ssp != null){
            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
            resCreateSoftwareDTO.setSoftwareCode(ssp.getSoftwareCode());
            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
            resCreateSoftwareDTO.setSoftwareConfig(ssp.getSoftwareConfig());
            return resCreateSoftwareDTO;
        }

        // 调用软件资源库获取软件的所有基本信息
        ServerSourceDo serverSourceDo = serverSourceBo.getSourceByNameAndVersion(reqCreateSoftwareDTO.getSoftwareName(), reqCreateSoftwareDTO.getVersion());
        if(serverSourceDo == null){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件资源不存在请检查");
        }
        //构建软件环境的配置
        JSONObject jsonObject = JSON.parseObject(serverSourceDo.getSourceConfig());
        jsonObject.replace("ip", reqCreateSoftwareDTO.getIp());
        jsonObject.replace("dataSourceName", config.get("dataSourceName"));

        //构建脚本参数
        String softwareName = reqCreateSoftwareDTO.getSoftwareName();
        String version = reqCreateSoftwareDTO.getVersion();
        String downloadUrl = serverSourceDo.getSourceDownUrl();
        List<String> list = new ArrayList<>();
        list.add(softwareName);
        list.add(version);
        list.add(downloadUrl);
        list.add(config.get("dataSourceName"));
        if(StringUtils.isNotEmpty(config.get("sqlUrl"))){
            list.add(config.get("sqlUrl"));
        }

        String param = StringUtils.join(list, ",");

        //获取脚本并执行
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version, ScriptTypeConstant.INSTALL);
        return  execInstallScript(serverScriptDo.getScript(),param,jsonObject.toJSONString(),reqCreateSoftwareDTO);
    }



    private ResCreateSoftwareDTO installKafka(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        Map<String, String> config = reqCreateSoftwareDTO.getConfig();
        if(StringUtils.isEmpty(config.get("zookeeperCluster"))){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"创建失败,未指定zookeeper集群地址");
        }
        ServerSoftwareProfileDo ssp = serverSoftwareProfileBo.getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(
                reqCreateSoftwareDTO.getIp(),
                reqCreateSoftwareDTO.getSoftwareName(),
                reqCreateSoftwareDTO.getVersion());
        //存在直接返回
        if(ssp != null){
            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
            resCreateSoftwareDTO.setSoftwareCode(ssp.getSoftwareCode());
            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
            resCreateSoftwareDTO.setSoftwareConfig(ssp.getSoftwareConfig());
            return resCreateSoftwareDTO;
        }
        // 调用软件资源库获取软件的所有基本信息
        ServerSourceDo serverSourceDo = serverSourceBo.getSourceByNameAndVersion(reqCreateSoftwareDTO.getSoftwareName(), reqCreateSoftwareDTO.getVersion());
        if(serverSourceDo == null){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件资源不存在请检查");
        }
        //构建软件环境的配置
        JSONObject jsonObject = JSON.parseObject(serverSourceDo.getSourceConfig());
        jsonObject.replace("ip", reqCreateSoftwareDTO.getIp());
        jsonObject.replace("zookeeperCluster", config.get("zookeeperCluster"));
        //构建脚本参数
        String softwareName = reqCreateSoftwareDTO.getSoftwareName();
        String version = reqCreateSoftwareDTO.getVersion();
        String downloadUrl = serverSourceDo.getSourceDownUrl();
        List<String> list = new ArrayList<>();
        list.add(softwareName);
        list.add(version);
        list.add(downloadUrl);
        list.add(config.get("zookeeperCluster"));
        String param = StringUtils.join(list, ",");

        //获取脚本并执行
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version, ScriptTypeConstant.INSTALL);
        return  execInstallScript(serverScriptDo.getScript(),param,jsonObject.toJSONString(),reqCreateSoftwareDTO);
    }

    private ResCreateSoftwareDTO installDocker(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        //TODO docker install
        throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"docker 安装暂未实现");
    }

    private ResCreateSoftwareDTO installElasticsearch(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        //TODO es install
        throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"es 安装暂未实现");
    }

    private ResCreateSoftwareDTO installFlume(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        //TODO flume install
        throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"flume 安装暂未实现");
    }

    private ResCreateSoftwareDTO installMongodb(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        ServerSoftwareProfileDo ssp = serverSoftwareProfileBo.getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(
                reqCreateSoftwareDTO.getIp(),
                reqCreateSoftwareDTO.getSoftwareName(),
                reqCreateSoftwareDTO.getVersion());
        //存在直接返回
        if(ssp != null){
            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
            resCreateSoftwareDTO.setSoftwareCode(ssp.getSoftwareCode());
            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
            resCreateSoftwareDTO.setSoftwareConfig(ssp.getSoftwareConfig());
            return resCreateSoftwareDTO;
        }
        // 调用软件资源库获取软件的所有基本信息
        ServerSourceDo serverSourceDo = serverSourceBo.getSourceByNameAndVersion(reqCreateSoftwareDTO.getSoftwareName(), reqCreateSoftwareDTO.getVersion());
        if(serverSourceDo == null){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件资源不存在请检查");
        }
        //构建软件环境的配置
        JSONObject jsonObject = JSON.parseObject(serverSourceDo.getSourceConfig());
        jsonObject.replace("ip", reqCreateSoftwareDTO.getIp());

        //脚本参数
        String softwareName = reqCreateSoftwareDTO.getSoftwareName();
        String version = reqCreateSoftwareDTO.getVersion();
        String downloadUrl = serverSourceDo.getSourceDownUrl();
        List<String> list = new ArrayList<>();
        list.add(softwareName);
        list.add(version);
        list.add(downloadUrl);
        String param = StringUtils.join(list, ",");

        //获取脚本并执行
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version, ScriptTypeConstant.INSTALL);
        return  execInstallScript(serverScriptDo.getScript(),param,jsonObject.toJSONString(),reqCreateSoftwareDTO);
    }

    private ResCreateSoftwareDTO installRedis(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        ServerSoftwareProfileDo ssp = serverSoftwareProfileBo.getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(
                reqCreateSoftwareDTO.getIp(),
                reqCreateSoftwareDTO.getSoftwareName(),
                reqCreateSoftwareDTO.getVersion());
        //存在直接返回
        if(ssp != null){
            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
            resCreateSoftwareDTO.setSoftwareCode(ssp.getSoftwareCode());
            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
            resCreateSoftwareDTO.setSoftwareConfig(ssp.getSoftwareConfig());
            return resCreateSoftwareDTO;
        }
        // 调用软件资源库获取软件的所有基本信息
        ServerSourceDo serverSourceDo = serverSourceBo.getSourceByNameAndVersion(reqCreateSoftwareDTO.getSoftwareName(), reqCreateSoftwareDTO.getVersion());
        if(serverSourceDo == null){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件资源不存在请检查");
        }
        //构建软件环境的配置
        JSONObject jsonObject = JSON.parseObject(serverSourceDo.getSourceConfig());
        jsonObject.replace("ip", reqCreateSoftwareDTO.getIp());

        //脚本参数
        String softwareName = reqCreateSoftwareDTO.getSoftwareName();
        String version = reqCreateSoftwareDTO.getVersion();
        String downloadUrl = serverSourceDo.getSourceDownUrl();
        List<String> list = new ArrayList<>();
        list.add(softwareName);
        list.add(version);
        list.add(downloadUrl);
        String param = StringUtils.join(list, ",");

        //获取脚本并执行
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version, ScriptTypeConstant.INSTALL);
        return  execInstallScript(serverScriptDo.getScript(),param,jsonObject.toJSONString(),reqCreateSoftwareDTO);
    }

    private ResCreateSoftwareDTO installZookeeper(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        ServerSoftwareProfileDo ssp = serverSoftwareProfileBo.getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(
                reqCreateSoftwareDTO.getIp(),
                reqCreateSoftwareDTO.getSoftwareName(),
                reqCreateSoftwareDTO.getVersion());
        //存在直接返回
        if(ssp != null){
            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
            resCreateSoftwareDTO.setSoftwareCode(ssp.getSoftwareCode());
            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
            resCreateSoftwareDTO.setSoftwareConfig(ssp.getSoftwareConfig());
            return resCreateSoftwareDTO;
        }
        // 调用软件资源库获取软件的所有基本信息
        ServerSourceDo serverSourceDo = serverSourceBo.getSourceByNameAndVersion(reqCreateSoftwareDTO.getSoftwareName(), reqCreateSoftwareDTO.getVersion());
        if(serverSourceDo == null){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件资源不存在请检查");
        }
        //构建软件环境的配置
        JSONObject jsonObject = JSON.parseObject(serverSourceDo.getSourceConfig());
        jsonObject.replace("ip", reqCreateSoftwareDTO.getIp());

        //脚本参数
        String softwareName = reqCreateSoftwareDTO.getSoftwareName();
        String version = reqCreateSoftwareDTO.getVersion();
        String downloadUrl = serverSourceDo.getSourceDownUrl();
        List<String> list = new ArrayList<>();
        list.add(softwareName);
        list.add(version);
        list.add(downloadUrl);
        String param = StringUtils.join(list, ",");

        //获取脚本并执行
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version, ScriptTypeConstant.INSTALL);
        return  execInstallScript(serverScriptDo.getScript(),param,jsonObject.toJSONString(),reqCreateSoftwareDTO);
    }

    private ResCreateSoftwareDTO installCassandra(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        ServerSoftwareProfileDo ssp = serverSoftwareProfileBo.getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(
                reqCreateSoftwareDTO.getIp(),
                reqCreateSoftwareDTO.getSoftwareName(),
                reqCreateSoftwareDTO.getVersion());
        //存在直接返回
        if(ssp != null){
            ResCreateSoftwareDTO resCreateSoftwareDTO = new ResCreateSoftwareDTO();
            resCreateSoftwareDTO.setSoftwareCode(ssp.getSoftwareCode());
            resCreateSoftwareDTO.setConfigFlag(CommonConstant.CODE1);
            resCreateSoftwareDTO.setSoftwareConfig(ssp.getSoftwareConfig());
            return resCreateSoftwareDTO;
        }
        // 调用软件资源库获取软件的所有基本信息
        ServerSourceDo serverSourceDo = serverSourceBo.getSourceByNameAndVersion(reqCreateSoftwareDTO.getSoftwareName(), reqCreateSoftwareDTO.getVersion());
        if(serverSourceDo == null){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件资源不存在请检查");
        }
        //构建软件环境的配置
        JSONObject jsonObject = JSON.parseObject(serverSourceDo.getSourceConfig());
        jsonObject.replace("ip", reqCreateSoftwareDTO.getIp());

        //脚本参数
        String softwareName = reqCreateSoftwareDTO.getSoftwareName();
        String version = reqCreateSoftwareDTO.getVersion();
        String downloadUrl = serverSourceDo.getSourceDownUrl();
        List<String> list = new ArrayList<>();
        list.add(softwareName);
        list.add(version);
        list.add(downloadUrl);
        String param = StringUtils.join(list, ",");

        //获取脚本并执行
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version, ScriptTypeConstant.INSTALL);
        return  execInstallScript(serverScriptDo.getScript(),param,jsonObject.toJSONString(),reqCreateSoftwareDTO);
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
                if(copyOnWriteArrayList.stream().noneMatch(softwareInstallInfo -> softwareInstallInfo.getProfileCode().equals(first.get().getProfileCode()))){
                    //  通知项目模块环境创建完成
                    UpdateProjectStatusVo updateProjectStatusVo = new UpdateProjectStatusVo();
                    updateProjectStatusVo.setCode(first.get().getProfileCode());
                    updateProjectStatusVo.setType(Integer.valueOf(CommonConstant.CODE2));
                    projectBo.updateProjectStatus(updateProjectStatusVo);
                }
            }
            else {
                // TODO 通知项目模块环境创建失败 保留项(暂不实现)
                //移除列表中所有该项目相关的东西
                copyOnWriteArrayList.removeIf(softwareInstallInfo -> softwareInstallInfo.getProfileCode().equals(String.valueOf(first.get().getProfileCode())));
            }
        }
    }


    private void checkParam(ReqCreateSoftwareDTO reqCreateSoftwareDTO){
        if(StringUtils.isEmpty(reqCreateSoftwareDTO.getProfileCode())){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"传入的profileCode为空");
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
        // 该服务器是否处于托管中?
        ServerDo serverDoByIp = serverBo.getServerDoByIp(reqCreateSoftwareDTO.getIp());
        if(serverDoByIp == null || CommonConstant.CODE1.equals(serverDoByIp.getServerStatus())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"服务器不处于托管中");
        }

    }

     static class SoftwareInstallInfo {

        private String selectId;

        private String softwareName;

        private String profileCode;

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

         public String getProfileCode() {
             return profileCode;
         }

         public void setProfileCode(String profileCode) {
             this.profileCode = profileCode;
         }
     }

}
