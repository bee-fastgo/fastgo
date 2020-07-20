package com.bee.team.fastgo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FastGoConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastGoConfigApplication.class);
    }
}
