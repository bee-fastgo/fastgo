package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.UserRolePermissionDoMapperExt;
import com.bee.team.fastgo.model.UserRolePermissionDo;
import com.bee.team.fastgo.model.UserRolePermissionDoExample;
import com.bee.team.fastgo.service.user.UserRolePermissionBo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRolePermissionBoImpl extends AbstractLavaBoImpl<UserRolePermissionDo, UserRolePermissionDoMapperExt, UserRolePermissionDoExample> implements UserRolePermissionBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(UserRolePermissionDoMapperExt mapper) {
        setMapper(mapper);
    }
}