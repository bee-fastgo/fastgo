package com.bee.team.fastgo.controller;

import com.acl.xauth.anno.authc.NoAuth;
import com.alibaba.lava.privilege.PrivilegeInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DemoController {
    @Autowired
    private TestDemoBo testDemoBo;
    @Autowired
    PrivilegeInfo privilegeInfo;
//    @NoAuth
//    @RequestMapping("/index")
//    public String index(HttpServletRequest request,HttpServletResponse response) {
//       AccountUtils.getCurrentAccount();
//        return AccountUtils.getCurrentAccount().toString();
//    }
    @NoAuth
    @RequestMapping("/noPermission")
    public String noPermission() {
        return "noPermission";
    }
    @RequestMapping("/testPermission")
    public String testPermission(HttpServletRequest request,HttpServletResponse response) throws IOException {
        testDemoBo.getData("1");
        return "testPermission";
    }
}