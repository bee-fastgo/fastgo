package com.bee.team.fastgo.job.core.executor.core.config;

import com.bee.team.fastgo.job.core.executor.impl.SimpleJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * simple-job config
 *
 * @author luke 2017-04-28
 */
@Configuration
public class SimpleJobConfig {
    private Logger logger = LoggerFactory.getLogger(SimpleJobConfig.class);

    @Value("${simple.job.admin.addresses}")
    private String adminAddresses;

    @Value("${simple.job.accessToken}")
    private String accessToken;

    @Value("${simple.job.executor.appname}")
    private String appname;

    @Value("${simple.job.executor.address}")
    private String address;

    @Value("${simple.job.executor.ip}")
    private String ip;

    @Value("${simple.job.executor.port}")
    private int port;

    @Value("${simple.job.executor.logpath}")
    private String logPath;

    @Value("${simple.job.executor.logretentiondays}")
    private int logRetentionDays;


    @Bean
    public SimpleJobSpringExecutor simpleJobExecutor() {
        logger.info(">>>>>>>>>>> simple-job config init.");
        SimpleJobSpringExecutor simpleJobSpringExecutor = new SimpleJobSpringExecutor();
        simpleJobSpringExecutor.setAdminAddresses(adminAddresses);
        simpleJobSpringExecutor.setAppname(appname);
        simpleJobSpringExecutor.setAddress(address);
        simpleJobSpringExecutor.setIp(ip);
        simpleJobSpringExecutor.setPort(port);
        simpleJobSpringExecutor.setAccessToken(accessToken);
        simpleJobSpringExecutor.setLogPath(logPath);
        simpleJobSpringExecutor.setLogRetentionDays(logRetentionDays);

        return simpleJobSpringExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */


}