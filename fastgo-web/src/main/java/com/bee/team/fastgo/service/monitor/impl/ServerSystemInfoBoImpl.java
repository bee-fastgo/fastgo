package com.bee.team.fastgo.service.monitor.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.exception.sever.MonitorException;
import com.bee.team.fastgo.mapper.ServerSystemInfoDoMapperExt;
import com.bee.team.fastgo.model.ServerSystemInfoDo;
import com.bee.team.fastgo.model.ServerSystemInfoDoExample;
import com.bee.team.fastgo.service.monitor.ServerSystemInfoBo;
import com.bee.team.fastgo.vo.monitor.res.ServerSystemInfoVo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@IsApiService
public class ServerSystemInfoBoImpl extends AbstractLavaBoImpl<ServerSystemInfoDo, ServerSystemInfoDoMapperExt, ServerSystemInfoDoExample> implements ServerSystemInfoBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerSystemInfoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void saveSystemInfoBoLog(ServerSystemInfoDo serverSystemInfoDo) {
        if (ObjectUtils.isEmpty(getServerSystemInfoDo(serverSystemInfoDo.getServerIp()))) {
            mapper.insertSelective(serverSystemInfoDo);
        }
    }

    @Override
    public ServerSystemInfoVo getServerSystemInfoDo(String serverIp) {
        ServerSystemInfoVo vo = new ServerSystemInfoVo();
        ServerSystemInfoDoExample example = new ServerSystemInfoDoExample();
        example.createCriteria().andServerIpEqualTo(serverIp);
        List<ServerSystemInfoDo> serverSystemInfoDoList = selectByExample(example);
        if (!CollectionUtils.isEmpty(serverSystemInfoDoList)) {
            vo = baseSupport.objectCopy(serverSystemInfoDoList.get(0), ServerSystemInfoVo.class);
        }
        return vo;
    }

}