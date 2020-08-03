package com.bee.team.fastgo.controller.user;

import com.bee.team.fastgo.common.CommonLoginValue;
import com.bee.team.fastgo.service.user.UserBo;
import com.bee.team.fastgo.vo.user.UserLoginReqVo;
import com.spring.simple.development.core.annotation.base.NoLogin;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Description 登录管理
 * @Author xqx
 * @Date 2020/7/23 16:07
 * @Version 1.0
 **/
@Api(tags = "登录管理")
@RequestMapping("/user")
@RestController
public class LoginController {
    @Autowired
    private UserBo userBo;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    @ValidHandler(key = "userLoginReqVo", value = UserLoginReqVo.class, isReqBody = false)
    @NoLogin
    public ResBody userLogin(HttpServletRequest httpServletRequest, @RequestBody UserLoginReqVo userLoginReqVo) {
        userBo.login(httpServletRequest, userLoginReqVo.getUserName(), userLoginReqVo.getPassword());
        return new ResBody().buildSuccessResBody(userLoginReqVo.getUserName());
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    @ApiOperation(value = "用户登出")
    public ResBody userLoginOut(HttpServletRequest request) {
        // 将信息保存到session
        HttpSession session = request.getSession();
        // 如果不存在session就新增session
        session.removeAttribute(CommonLoginValue.SESSION_LOGIN_KEY);
        return new ResBody().buildSuccessResBody();
    }

}
