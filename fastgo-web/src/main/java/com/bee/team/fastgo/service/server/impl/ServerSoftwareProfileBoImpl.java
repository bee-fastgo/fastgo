package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ServerSoftwareProfileDoMapperExt;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDoExample;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@IsApiService
public class ServerSoftwareProfileBoImpl extends AbstractLavaBoImpl<ServerSoftwareProfileDo, ServerSoftwareProfileDoMapperExt, ServerSoftwareProfileDoExample> implements ServerSoftwareProfileBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerSoftwareProfileDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public ServerSoftwareProfileDo getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(String ip, String softwareName, String version) {

        /*
        ServerIp:服务器的ip (例192.168.1.100)
        SoftwareCode:软件名,来源于字典 (例mysql)
        SoftwareName:SoftwareCode+version,中间用-分隔 (列mysql-5.7)
         */
        ServerSoftwareProfileDoExample serverSoftwareProfileDoExample = new ServerSoftwareProfileDoExample();
        serverSoftwareProfileDoExample.createCriteria().andServerIpEqualTo(ip)
                .andSoftwareCodeEqualTo(softwareName)
                .andSoftwareNameEqualTo(softwareName + "-" + version);
        List<ServerSoftwareProfileDo> serverSoftwareProfileDos = selectByExample(serverSoftwareProfileDoExample);
        return CollectionUtils.isNotEmpty(serverSoftwareProfileDos)?serverSoftwareProfileDos.get(0):null;
    }


    @Override
    public void saveServerSoftwareProfile(ServerSoftwareProfileDo serverSoftwareProfileDo) {
        insert(serverSoftwareProfileDo);
    }
}