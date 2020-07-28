package com.bee.team.fastgo.job.core.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author luke 2018-10-28 00:38:13
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SimpleJobExecutorApplication {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        SpringApplication.run(SimpleJobExecutorApplication.class, args);
    }

}