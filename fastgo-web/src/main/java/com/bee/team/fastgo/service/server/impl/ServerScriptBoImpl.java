package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ServerScriptDoMapperExt;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.model.ServerScriptDoExample;
import com.bee.team.fastgo.service.server.ServerScriptBo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@IsApiService
public class ServerScriptBoImpl extends AbstractLavaBoImpl<ServerScriptDo, ServerScriptDoMapperExt, ServerScriptDoExample> implements ServerScriptBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerScriptDoMapperExt mapper) {
        setMapper(mapper);
    }
}