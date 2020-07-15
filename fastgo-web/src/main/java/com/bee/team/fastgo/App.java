package com.bee.team.fastgo;


import com.spring.simple.development.core.annotation.config.*;
import com.spring.simple.development.core.baseconfig.tomcat.SimpleApplication;

/**
 * @Author luke
 * @Description 程序启动
 **/
@EnableAlert
@EnableRedis
@EnableWebMvc
@EnableSwagger
@EnableMybatis
@EnableDataProcess
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}