package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.AlertInfoDoMapperExt;
import com.bee.team.fastgo.model.AlertInfoDo;
import com.bee.team.fastgo.model.AlertInfoDoExample;
import com.bee.team.fastgo.service.server.AlertInfoBo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@IsApiService
public class AlertInfoBoImpl extends AbstractLavaBoImpl<AlertInfoDo, AlertInfoDoMapperExt, AlertInfoDoExample> implements AlertInfoBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(AlertInfoDoMapperExt mapper) {
        setMapper(mapper);
    }
}