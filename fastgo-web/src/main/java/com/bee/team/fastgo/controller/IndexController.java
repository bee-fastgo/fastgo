package com.bee.team.fastgo.controller;

import com.spring.simple.development.core.annotation.base.NoLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liko.wang
 * @Date 2020/4/10/010 19:14
 * @Description //TODO
 **/
@Api(tags = "帮助")
@RestController
public class IndexController {
    @NoLogin
    @ApiParam(value = "获取帮助")
    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "Only oneself can help oneself";
    }
}