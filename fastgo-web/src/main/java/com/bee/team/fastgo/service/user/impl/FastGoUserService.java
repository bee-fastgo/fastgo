package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.privilege.PrivilegeInfo;
import com.bee.team.fastgo.common.CommonLoginValue;
import com.spring.simple.development.core.baseconfig.user.SimpleSessionProfile;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author luke
 * @desc 用户登录
 * @date 2020-08-06
 **/
@Service("simpleSessionProfile")
public class FastGoUserService implements SimpleSessionProfile {
    @Override
    public PrivilegeInfo getPrivilegeInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        HttpSession session = httpServletRequest.getSession();
        if (ObjectUtils.isEmpty(session.getAttribute(CommonLoginValue.SESSION_LOGIN_KEY))) {
            throw new GlobalException(GlobalResponseCode.SYS_NO_LOGIN);
        }
        return new PrivilegeInfo();
    }

    @Override
    public String privilegeInfoLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public void privilegeInfoLogout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }
}
