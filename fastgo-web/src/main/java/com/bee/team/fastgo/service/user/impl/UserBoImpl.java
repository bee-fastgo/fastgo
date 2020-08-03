package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.common.CommonLoginValue;
import com.bee.team.fastgo.mapper.UserDoMapperExt;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.model.UserDoExample;
import com.bee.team.fastgo.service.user.UserBo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public void login(HttpServletRequest request, String userName, String password) {
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
        // 将信息保存到session
        HttpSession session = request.getSession();
        // 如果不存在session就新增session
        if (ObjectUtils.isEmpty(session.getAttribute(userName))) {
            session.setAttribute(CommonLoginValue.SESSION_LOGIN_KEY, userDo);
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

    @Override
    public ResPageDTO ListUsers(Integer pageNum, Integer pageSize, String name) {
        return null;
    }
}