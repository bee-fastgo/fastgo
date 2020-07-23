package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.UserDoMapperExt;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.model.UserDoExample;
import com.bee.team.fastgo.service.user.UserBo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.spring.simple.development.support.exception.ResponseCode.*;

@Service
public class UserBoImpl extends AbstractLavaBoImpl<UserDo, UserDoMapperExt, UserDoExample> implements UserBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(UserDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void login(String userName, String password) {
        UserDoExample example = new UserDoExample();
        example.createCriteria().andUserNameEqualTo(userName).andIsDeletedEqualTo("n");
        List<UserDo> list = selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "用户不存在");
        }
        UserDo userDo = list.get(0);

        if (!StringUtils.equals(userDo.getUserPassword(), Md5Utils.getTwoMD5Str(password))) {
            throw new GlobalException(RES_PWD_OR_CODE_INVALID, "用户名或密码错误");
        }
    }

    @Override
    public void insertUser(String userName, String password) {
        UserDoExample example = new UserDoExample();
        example.createCriteria().andUserNameEqualTo(userName).andIsDeletedEqualTo("n");
        List<UserDo> list = selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            throw new GlobalException(RES_DATA_EXIST, "用户已存在");
        }
        UserDo userDo = new UserDo();
        userDo.setUserName(userName);
        userDo.setUserPassword(Md5Utils.getTwoMD5Str(password));

        insert(userDo);
    }


}