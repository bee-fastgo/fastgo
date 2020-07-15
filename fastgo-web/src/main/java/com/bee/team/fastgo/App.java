package com.bee.team.fastgo;


import com.spring.simple.development.core.annotation.config.*;
import com.spring.simple.development.core.baseconfig.tomcat.SimpleApplication;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:20
 * @Description 程序启动
 **/
//@EnableAlert
//@EnableXxlJob
@EnableSwagger
@EnableMybatis
//@EnableRedis
@EnableWebMvc
//@EnableDubbo
//@EnableCassandra
//@EnableShiroCas
@EnableDataProcess
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}