package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.UserRoleDoMapperExt;
import com.bee.team.fastgo.model.UserRoleDo;
import com.bee.team.fastgo.model.UserRoleDoExample;
import com.bee.team.fastgo.service.user.UserRoleBo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleBoImpl extends AbstractLavaBoImpl<UserRoleDo, UserRoleDoMapperExt, UserRoleDoExample> implements UserRoleBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(UserRoleDoMapperExt mapper) {
        setMapper(mapper);
    }
}