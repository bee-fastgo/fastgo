package com.bee.team.fastgo.controller;

import com.bee.team.fastgo.service.user.UserBo;
import com.bee.team.fastgo.vo.user.AddUserReqVo;
import com.bee.team.fastgo.vo.user.UserLoginReqVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description 登录管理
 * @Author xqx
 * @Date 2020/7/23 16:07
 * @Version 1.0
 **/
@Api(tags = "登录管理")
@RestController("/user")
public class LoginController {
    @Autowired
    private UserBo userBo;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    @ValidHandler(key = "userLoginReqVo", value = UserLoginReqVo.class, isReqBody = false)
    public ResBody userLogin(@RequestBody UserLoginReqVo userLoginReqVo) {
        userBo.login(userLoginReqVo.getUserName(), userLoginReqVo.getPassword());
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/insertLogin", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册")
    @ValidHandler(key = "addUserReqVo", value = AddUserReqVo.class, isReqBody = false)
    public ResBody userAdd(@RequestBody AddUserReqVo addUserReqVo) {
        userBo.insertUser(addUserReqVo.getUserName(), addUserReqVo.getPassword());
        return new ResBody().buildSuccessResBody();
    }
}
