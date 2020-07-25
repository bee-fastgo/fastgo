package com.bee.team.fastgo.service.monitor.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ServerNetIoLogDoMapperExt;
import com.bee.team.fastgo.model.ServerNetIOLogDo;
import com.bee.team.fastgo.model.ServerNetIOLogDoExample;
import com.bee.team.fastgo.service.monitor.ServerNetIOLogBo;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerNetIOLogVo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@IsApiService
public class ServerNetIOLogBoImpl extends AbstractLavaBoImpl<ServerNetIOLogDo, ServerNetIoLogDoMapperExt, ServerNetIOLogDoExample> implements ServerNetIOLogBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerNetIoLogDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void saveNetIOLog(ServerNetIOLogDo serverNetIOLogDo) {
        mapper.insertSelective(serverNetIOLogDo);
    }

    @Override
    public List<ServerNetIOLogVo> getServerNetIOLogList(ServerMonitorLogReqVo reqVo) {
        List<ServerNetIOLogVo> resultList = new ArrayList<>();
        ServerNetIOLogDoExample example = new ServerNetIOLogDoExample();
        example.createCriteria().andServerIpEqualTo(reqVo.getServerIp());
        List<ServerNetIOLogDo> serverNetIOLogDoList = selectByExample(example);
        if (!CollectionUtils.isEmpty(serverNetIOLogDoList)) {
            resultList = baseSupport.listCopy(serverNetIOLogDoList, ServerNetIOLogVo.class);
        }
        return resultList;
    }


}