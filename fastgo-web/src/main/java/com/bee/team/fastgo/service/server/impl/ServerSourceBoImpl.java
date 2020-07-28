package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ServerSourceDoMapperExt;
import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.model.ServerSourceDoExample;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@IsApiService
public class ServerSourceBoImpl extends AbstractLavaBoImpl<ServerSourceDo, ServerSourceDoMapperExt, ServerSourceDoExample> implements ServerSourceBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerSourceDoMapperExt mapper) {
        setMapper(mapper);
    }
}