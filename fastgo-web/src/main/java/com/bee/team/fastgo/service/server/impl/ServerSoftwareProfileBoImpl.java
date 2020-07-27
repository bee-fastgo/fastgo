package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.exception.sever.ScriptException;
import com.bee.team.fastgo.mapper.ServerSoftwareProfileDoMapperExt;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDoExample;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.bee.team.fastgo.vo.server.AddEnvironmentVo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.RandomUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@IsApiService
public class ServerSoftwareProfileBoImpl extends AbstractLavaBoImpl<ServerSoftwareProfileDo, ServerSoftwareProfileDoMapperExt, ServerSoftwareProfileDoExample> implements ServerSoftwareProfileBo {


    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerSoftwareProfileDoMapperExt mapper) {
        setMapper(mapper);
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

    @Override
    public void createEnvironment(AddEnvironmentVo addEnvironmentVo) {
        ServerSoftwareProfileDo dbServerSoftwareProfileDo = getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(addEnvironmentVo.getServerIp(), addEnvironmentVo.getSoftwareName(), addEnvironmentVo.getVersion());
        if(StringUtils.isNotEmpty(dbServerSoftwareProfileDo.getSoftwareCode())){
            throw new GlobalException(ScriptException.ENV_ABNORMAL, "环境已存在");
        }
        ServerSoftwareProfileDo serverSoftwareProfileDo = baseSupport.objectCopy(addEnvironmentVo, ServerSoftwareProfileDo.class);

        serverSoftwareProfileDo.setSoftwareCode(RandomUtil.randomAll(16));
//        serverSoftwareProfileDo.setSoftwareConfig();
        saveServerSoftwareProfile(serverSoftwareProfileDo);
    }
}