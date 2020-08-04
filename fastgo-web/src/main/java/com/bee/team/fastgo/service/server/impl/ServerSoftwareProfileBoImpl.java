package com.bee.team.fastgo.service.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.exception.sever.ScriptException;
import com.bee.team.fastgo.mapper.ProfileSoftwareRelationDoMapperExt;
import com.bee.team.fastgo.mapper.ServerSoftwareProfileDoMapperExt;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.service.api.server.ScriptApi;
import com.bee.team.fastgo.service.api.server.SoftwareProfileApi;
import com.bee.team.fastgo.service.api.server.dto.req.ReqCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.dto.req.ReqExecUnInstallScriptDTO;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.server.QuerySoftwareEnvironmentVo;
import com.bee.team.fastgo.vo.server.ReqAddEnvironmentVo;
import com.bee.team.fastgo.vo.server.ResSoftwareEnvironmentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ServerSoftwareProfileBoImpl extends AbstractLavaBoImpl<ServerSoftwareProfileDo, ServerSoftwareProfileDoMapperExt, ServerSoftwareProfileDoExample> implements ServerSoftwareProfileBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ProfileSoftwareRelationDoMapperExt profileSoftwareRelationDoMapperExt;

    @Autowired
    private ServerBo serverBo;

    @Autowired
    private ServerSourceBo serverSourceBo;

    @Autowired
    private ScriptApi scriptApi;

    @Autowired
    private SoftwareProfileApi softwareProfileApi;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerSoftwareProfileDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void createEnvironment(ReqAddEnvironmentVo reqAddEnvironmentVo) {
        ServerSoftwareProfileDo dbServerSoftwareProfileDo = getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(reqAddEnvironmentVo.getServerIp(), reqAddEnvironmentVo.getSoftwareName(), reqAddEnvironmentVo.getVersion());
        if (dbServerSoftwareProfileDo != null) {
            throw new GlobalException(ScriptException.ENV_ABNORMAL, "环境已存在");
        }
        if (Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).noneMatch(s -> s.equals(reqAddEnvironmentVo.getSoftwareName()))) {
            throw new GlobalException(ScriptException.ENV_ABNORMAL, "不支持的软件类型");
        }
        ServerSourceDo serverSourceDo = serverSourceBo.getSourceByNameAndVersion(reqAddEnvironmentVo.getSoftwareName(), reqAddEnvironmentVo.getVersion());
        if (serverSourceDo == null) {
            throw new GlobalException(ScriptException.ENV_ABNORMAL, "软件资源不存在请检查");
        }
        ServerDo serverDoByIp = serverBo.getServerDoByIp(reqAddEnvironmentVo.getServerIp());
        if (serverDoByIp == null) {
            throw new GlobalException(ScriptException.ENV_ABNORMAL, "该服务器不处于托管中");
        }

        //安装脚本
        ReqCreateSoftwareDTO reqCreateSoftwareDTO = new ReqCreateSoftwareDTO();
        reqCreateSoftwareDTO.setCreateEnvType(CommonConstant.CODE1);
        reqCreateSoftwareDTO.setIp(reqAddEnvironmentVo.getServerIp());
        reqCreateSoftwareDTO.setSoftwareName(reqAddEnvironmentVo.getSoftwareName());
        reqCreateSoftwareDTO.setVersion(reqAddEnvironmentVo.getVersion());
        reqCreateSoftwareDTO.setConfig(reqAddEnvironmentVo.getConfig());
        softwareProfileApi.createSoftwareEnvironment(reqCreateSoftwareDTO);

    }

    @Override
    public void deleteServerSoftwareProfileBySoftwareCode(String softwareCode) {
        // 查询是否有项目正在使用这个环境
        checkEnvironment(softwareCode);
        // 删除软件环境
        ServerSoftwareProfileDoExample serverSoftwareProfileDoExample = new ServerSoftwareProfileDoExample();
        serverSoftwareProfileDoExample.createCriteria().andSoftwareCodeEqualTo(softwareCode);
        List<ServerSoftwareProfileDo> serverSoftwareProfileDoList = selectByExample(serverSoftwareProfileDoExample);
        if (CollectionUtils.isEmpty(serverSoftwareProfileDoList)) {
            throw new GlobalException(ScriptException.ENV_ABNORMAL, "软件资源不存在请检查");
        }
        ServerSoftwareProfileDo serverSoftwareProfileDo = serverSoftwareProfileDoList.get(0);
        //删除
        serverSoftwareProfileDoExample.clear();
        serverSoftwareProfileDoExample.createCriteria().andSoftwareCodeEqualTo(softwareCode);
        deleteByExample(serverSoftwareProfileDoExample);

        // 执行删除环境脚本
        ReqExecUnInstallScriptDTO reqExecUnInstallScriptDTO = new ReqExecUnInstallScriptDTO();
        reqExecUnInstallScriptDTO.setIp(serverSoftwareProfileDo.getServerIp());
        reqExecUnInstallScriptDTO.setSoftwareName(serverSoftwareProfileDo.getSoftwareName());
        reqExecUnInstallScriptDTO.setVersion(serverSoftwareProfileDo.getVersion());
        scriptApi.execUninstallScript(reqExecUnInstallScriptDTO);
    }

    @Override
    public ResPageDTO<ResSoftwareEnvironmentVo> getSoftwareEnvironmentByPage(QuerySoftwareEnvironmentVo querySoftwareEnvironmentVo) {
        ServerSoftwareProfileDoExample serverSoftwareProfileDoExample = new ServerSoftwareProfileDoExample();
        if (StringUtils.isNotEmpty(querySoftwareEnvironmentVo.getSoftwareName())) {
            //是否支持该软件类型
            if (Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).noneMatch(s -> s.equals(querySoftwareEnvironmentVo.getSoftwareName()))) {
                throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "不支持的软件类型");
            }
            serverSoftwareProfileDoExample.createCriteria().andSoftwareNameEqualTo(querySoftwareEnvironmentVo.getSoftwareName());
        }

        //查询数据
        int startNo = querySoftwareEnvironmentVo.getStartPage();
        int rowCount = querySoftwareEnvironmentVo.getPageSize();
        PageHelper.startPage(startNo, rowCount);
        List<ServerSoftwareProfileDo> serverScriptDoList = selectByExample(serverSoftwareProfileDoExample);
        return baseSupport.pageCopy(new PageInfo(serverScriptDoList), ResSoftwareEnvironmentVo.class);
    }

    @Override
    public void updateSoftwareConfigBySoftwareCode(String softwareCode, String softwareConfig) {

    }

    @Override
    public ServerSoftwareProfileDo getMysqlProfileBySoftwareNameAndVersionIpAndPortAndDataSourceName(String softwareName, String version, String ip, String port, String dataSourceName) {
        ServerSoftwareProfileDoExample serverSoftwareProfileDoExample = new ServerSoftwareProfileDoExample();
        serverSoftwareProfileDoExample.createCriteria().andSoftwareNameEqualTo(softwareName)
                .andVersionEqualTo(version)
                .andServerIpEqualTo(ip);

        List<ServerSoftwareProfileDo> serverSoftwareProfileDoList = selectByExample(serverSoftwareProfileDoExample);
        if (CollectionUtils.isEmpty(serverSoftwareProfileDoList)){
            return null;
        }
        for (ServerSoftwareProfileDo serverSoftwareProfileDo : serverSoftwareProfileDoList) {
            JSONObject jsonObject = JSON.parseObject(serverSoftwareProfileDo.getSoftwareConfig());
            if (ip.equals(jsonObject.get("ip")) && port.equals(jsonObject.get("port")) && dataSourceName.equals(jsonObject.get("dataSourceName"))){
                return serverSoftwareProfileDo;
            }
        }
        return null;
    }


    @Override
    public ServerSoftwareProfileDo getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(String ip, String softwareName, String version) {

        /*
        ServerIp:服务器的ip (例192.168.1.100)
        SoftwareName:软件名,来源于字典 (例mysql)
        version:版本 (例5.7)
         */
        ServerSoftwareProfileDoExample serverSoftwareProfileDoExample = new ServerSoftwareProfileDoExample();
        serverSoftwareProfileDoExample.createCriteria().andServerIpEqualTo(ip)
                .andSoftwareNameEqualTo(softwareName)
                .andVersionEqualTo(version);
        List<ServerSoftwareProfileDo> serverSoftwareProfileDoList = selectByExample(serverSoftwareProfileDoExample);
        return CollectionUtils.isNotEmpty(serverSoftwareProfileDoList) ? serverSoftwareProfileDoList.get(0) : null;
    }


    @Override
    public void saveServerSoftwareProfile(ServerSoftwareProfileDo serverSoftwareProfileDo) {
        insert(serverSoftwareProfileDo);
    }

    @Override
    public ServerSoftwareProfileDo getServerSoftwareProfileBySoftwareCode(String softwareCode) {
        ServerSoftwareProfileDoExample serverSoftwareProfileDoExample = new ServerSoftwareProfileDoExample();
        serverSoftwareProfileDoExample.createCriteria().andSoftwareCodeEqualTo(softwareCode);
        List<ServerSoftwareProfileDo> serverSoftwareProfileDoList = selectByExample(serverSoftwareProfileDoExample);
        return CollectionUtils.isNotEmpty(serverSoftwareProfileDoList) ? serverSoftwareProfileDoList.get(0) : null;
    }

    private void checkEnvironment(String softwareCode) {
        ProfileSoftwareRelationDoExample profileSoftwareRelationDoExample = new ProfileSoftwareRelationDoExample();
        profileSoftwareRelationDoExample.createCriteria().andSoftwareCodeEqualTo(softwareCode);
        List<ProfileSoftwareRelationDo> profileSoftwareRelationDos = profileSoftwareRelationDoMapperExt.selectByExample(profileSoftwareRelationDoExample);
        if (CollectionUtils.isNotEmpty(profileSoftwareRelationDos)) {
            throw new GlobalException(ScriptException.ENV_ABNORMAL, "该环境正在启用中");
        }

        /*
            假设是mysql在移除时需要查询该服务是否被其他项目占用(因为一个服务中可能有多个库被不同的应用使用)
         */
        ServerSoftwareProfileDo serverSoftwareProfileDo = getServerSoftwareProfileBySoftwareCode(softwareCode);
        if(SoftwareEnum.MYSQL.name().toLowerCase().equals(serverSoftwareProfileDo.getSoftwareName())){
            List<ServerSoftwareProfileDo> serverSoftwareProfileDoList = getMysqlListByServerIpAndSoftwareNameAndVersion(serverSoftwareProfileDo.getServerIp(), serverSoftwareProfileDo.getSoftwareName(), serverSoftwareProfileDo.getVersion());
            for (ServerSoftwareProfileDo softwareProfileDo : serverSoftwareProfileDoList) {
                profileSoftwareRelationDoExample.clear();
                profileSoftwareRelationDoExample.createCriteria().andSoftwareCodeEqualTo(softwareProfileDo.getSoftwareCode());
                if (CollectionUtils.isNotEmpty(profileSoftwareRelationDoMapperExt.selectByExample(profileSoftwareRelationDoExample))) {
                    throw new GlobalException(ScriptException.ENV_ABNORMAL, "该数据库正被其他应用占用");
                }
            }

        }
    }

    private List<ServerSoftwareProfileDo> getMysqlListByServerIpAndSoftwareNameAndVersion(String ip, String softwareName, String version){
        ServerSoftwareProfileDoExample serverSoftwareProfileDoExample = new ServerSoftwareProfileDoExample();
        serverSoftwareProfileDoExample.createCriteria().andServerIpEqualTo(ip)
                .andSoftwareNameEqualTo(softwareName)
                .andVersionEqualTo(version);
        return selectByExample(serverSoftwareProfileDoExample);
    }
}