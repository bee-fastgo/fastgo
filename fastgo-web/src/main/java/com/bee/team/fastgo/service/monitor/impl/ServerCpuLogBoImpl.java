package com.bee.team.fastgo.service.monitor.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ServerCpuLogDoMapperExt;
import com.bee.team.fastgo.model.ServerCpuLogDo;
import com.bee.team.fastgo.model.ServerCpuLogDoExample;
import com.bee.team.fastgo.service.monitor.ServerCpuLogBo;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerCpuLogVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerCpuLogBoImpl extends AbstractLavaBoImpl<ServerCpuLogDo, ServerCpuLogDoMapperExt, ServerCpuLogDoExample> implements ServerCpuLogBo {

    @Autowired
    public void setBaseMapper(ServerCpuLogDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    private BaseSupport baseSupport;

    @Override
    public void saveCpuLog(ServerCpuLogDo serverCpuLogDo) {
        mapper.insertSelective(serverCpuLogDo);
    }

    @Override
    public List<ServerCpuLogVo> getServerCpuLogList(ServerMonitorLogReqVo reqVo) {
        List<ServerCpuLogVo> resultList = new ArrayList<>();
        ServerCpuLogDoExample example = new ServerCpuLogDoExample();
        example.createCriteria().andServerIpEqualTo(reqVo.getServerIp());
        List<ServerCpuLogDo> serverCpuLogDoList = selectByExample(example);
        if (!CollectionUtils.isEmpty(serverCpuLogDoList)) {
            resultList = baseSupport.listCopy(serverCpuLogDoList, ServerCpuLogVo.class);
        }
        return resultList;
    }


}