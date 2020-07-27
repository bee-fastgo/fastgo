package com.bee.team.fastgo.service.monitor.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ServerLoadLogDoMapperExt;
import com.bee.team.fastgo.model.ServerLoadLogDo;
import com.bee.team.fastgo.model.ServerLoadLogDoExample;
import com.bee.team.fastgo.service.monitor.ServerLoadLogBo;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerLoadLogVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerLoadLogBoImpl extends AbstractLavaBoImpl<ServerLoadLogDo, ServerLoadLogDoMapperExt, ServerLoadLogDoExample> implements ServerLoadLogBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    public void setBaseMapper(ServerLoadLogDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void saveLoadLog(ServerLoadLogDo serverLoadLogDo) {
        mapper.insertSelective(serverLoadLogDo);
    }

    @Override
    public List<ServerLoadLogVo> getServerLoadLogList(ServerMonitorLogReqVo reqVo) {
        List<ServerLoadLogVo> resultList = new ArrayList<>();
        ServerLoadLogDoExample example = new ServerLoadLogDoExample();
        example.createCriteria().andServerIpEqualTo(reqVo.getServerIp());
        List<ServerLoadLogDo> serverLoadLogDoList = selectByExample(example);
        if (!CollectionUtils.isEmpty(serverLoadLogDoList)) {
            resultList = baseSupport.listCopy(serverLoadLogDoList, ServerLoadLogVo.class);
        }
        return resultList;
    }


}