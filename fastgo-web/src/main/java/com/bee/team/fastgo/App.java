package com.bee.team.fastgo;


import com.spring.simple.development.core.annotation.config.*;
import com.spring.simple.development.core.baseconfig.tomcat.SimpleBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author luke
 * @Description 程序启动
 **/
@EnableAlert
@EnableWebMvc
@EnableSwagger
@EnableMybatis
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        SimpleBootApplication.run(App.class, args);
    }
}