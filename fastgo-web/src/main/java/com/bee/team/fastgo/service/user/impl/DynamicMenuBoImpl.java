package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.DynamicMenuDoMapperExt;
import com.bee.team.fastgo.model.DynamicMenuDo;
import com.bee.team.fastgo.model.DynamicMenuDoExample;
import com.bee.team.fastgo.service.user.DynamicMenuBo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicMenuBoImpl extends AbstractLavaBoImpl<DynamicMenuDo, DynamicMenuDoMapperExt, DynamicMenuDoExample> implements DynamicMenuBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DynamicMenuDoMapperExt mapper) {
        setMapper(mapper);
    }
}