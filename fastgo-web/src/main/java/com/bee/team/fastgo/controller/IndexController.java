package com.bee.team.fastgo.controller;

import com.spring.simple.development.core.annotation.base.NoLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author liko.wang
 * @Date 2020/4/10/010 19:14
 * @Description //TODO
 **/
@RestController
public class IndexController {

    @NoLogin
    @RequestMapping("/noPermission")
    public String noPermission() {
        return "noPermission";
    }
}