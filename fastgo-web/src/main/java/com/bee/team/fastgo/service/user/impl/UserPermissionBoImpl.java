package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.UserPermissionDoMapperExt;
import com.bee.team.fastgo.model.UserPermissionDo;
import com.bee.team.fastgo.model.UserPermissionDoExample;
import com.bee.team.fastgo.service.user.UserPermissionBo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPermissionBoImpl extends AbstractLavaBoImpl<UserPermissionDo, UserPermissionDoMapperExt, UserPermissionDoExample> implements UserPermissionBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(UserPermissionDoMapperExt mapper) {
        setMapper(mapper);
    }
}