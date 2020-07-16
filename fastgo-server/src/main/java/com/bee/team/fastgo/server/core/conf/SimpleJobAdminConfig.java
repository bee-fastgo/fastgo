package com.bee.team.fastgo.server.core.conf;

import com.bee.team.fastgo.server.core.scheduler.SimpleJobScheduler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * simple-job config
 *
 * @author luke 2017-04-28
 */

@Component
public class SimpleJobAdminConfig implements InitializingBean, DisposableBean {

    private static SimpleJobAdminConfig adminConfig = null;

    public static SimpleJobAdminConfig getAdminConfig() {
        return adminConfig;
    }


    // ---------------------- XxlJobScheduler ----------------------

    private SimpleJobScheduler simpleJobScheduler;

    @Override
    public void afterPropertiesSet() throws Exception {
        adminConfig = this;

        simpleJobScheduler = new SimpleJobScheduler();
        simpleJobScheduler.init();
    }

    @Override
    public void destroy() throws Exception {
        simpleJobScheduler.destroy();
    }


    // ---------------------- XxlJobScheduler ----------------------


    @Value("${spring.simple.job.accessToken}")
    private String accessToken;

    @Value("${spring.simple.job.triggerpool.fast.max}")
    private int triggerPoolFastMax;

    @Value("${spring.simple.job.triggerpool.slow.max}")
    private int triggerPoolSlowMax;

    @Value("${spring.simple.job.logretentiondays}")
    private int logretentiondays;



    public String getAccessToken() {
        return accessToken;
    }



    public int getTriggerPoolFastMax() {
        if (triggerPoolFastMax < 200) {
            return 200;
        }
        return triggerPoolFastMax;
    }

    public int getTriggerPoolSlowMax() {
        if (triggerPoolSlowMax < 100) {
            return 100;
        }
        return triggerPoolSlowMax;
    }

    public int getLogretentiondays() {
        if (logretentiondays < 7) {
            return -1;  // Limit greater than or equal to 7, otherwise close
        }
        return logretentiondays;
    }
}
