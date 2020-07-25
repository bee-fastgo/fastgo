package com.bee.team.fastgo.service.monitor.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.exception.sever.MonitorException;
import com.bee.team.fastgo.mapper.ServerMemoryLogDoMapperExt;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.model.ServerMemoryLogDo;
import com.bee.team.fastgo.service.monitor.ServerMemoryLogBo;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerMemoryLogVo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@IsApiService
public class ServerMemoryLogBoImpl extends AbstractLavaBoImpl<ServerMemoryLogDo, ServerMemoryLogDoMapperExt, ServerMemoryLogDoExample> implements ServerMemoryLogBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerMemoryLogDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void saveMemoryLog(ServerMemoryLogDo serverMemoryLogDo) {
        mapper.insertSelective(serverMemoryLogDo);
    }

    @Override
    public List<ServerMemoryLogVo> getServerMemoryLogList(ServerMonitorLogReqVo reqVo) {
        List<ServerMemoryLogVo> resultList = new ArrayList<>();
        ServerMemoryLogDoExample example = new ServerMemoryLogDoExample();
        example.createCriteria().andServerIpEqualTo(reqVo.getServerIp());
        List<ServerMemoryLogDo> serverMemoryLogDoList = selectByExample(example);
        if (!CollectionUtils.isEmpty(serverMemoryLogDoList)) {
            resultList = baseSupport.listCopy(serverMemoryLogDoList, ServerMemoryLogVo.class);
        }
        return resultList;
    }

}