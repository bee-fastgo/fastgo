package com.bee.team.fastgo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: luke
 * @time: 2020/7/15 0015 10:02
 */
@Configuration
public class Config {
    @Value("${spring.data.mongodb.authentication-database}")
    private String auth;
    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private String port;
    @Value("${spring.data.mongodb.database}")
    private String database;
    @Value("${spring.data.mongodb.username}")
    private String userName;
    @Value("${spring.data.mongodb.password}")
    private String password;
}
