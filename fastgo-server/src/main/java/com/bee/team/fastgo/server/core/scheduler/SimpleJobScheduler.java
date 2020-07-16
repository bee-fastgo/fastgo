package com.bee.team.fastgo.server.core.scheduler;

import com.alibaba.lava.util.I18nUtil;
import com.bee.team.fastgo.job.core.biz.client.ExecutorBizClient;
import com.bee.team.fastgo.server.core.conf.SimpleJobAdminConfig;
import com.bee.team.fastgo.server.core.thread.JobTriggerPoolHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bee.team.fastgo.job.core.biz.ExecutorBiz;
import com.bee.team.fastgo.job.core.enums.ExecutorBlockStrategyEnum;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author luke 2018-10-28 00:18:17
 */

public class SimpleJobScheduler {
    private static final Logger logger = LoggerFactory.getLogger(SimpleJobScheduler.class);


    public void init() throws Exception {
        // init i18n

//        // admin registry monitor run
//        JobRegistryMonitorHelper.getInstance().start();
//
//        // admin fail-monitor run
//        JobFailMonitorHelper.getInstance().start();
//
//        // admin lose-monitor run
//        JobLosedMonitorHelper.getInstance().start();

        // admin trigger pool start
        JobTriggerPoolHelper.toStart();

//        // admin log report start
//        JobLogReportHelper.getInstance().start();


        logger.info(">>>>>>>>> init simple-job admin success.");
    }

    
    public void destroy() throws Exception {


        // admin log report stop
//        JobLogReportHelper.getInstance().toStop();

        // admin trigger pool stop
        JobTriggerPoolHelper.toStop();

//        // admin lose-monitor stop
//        JobLosedMonitorHelper.getInstance().toStop();
//
//        // admin fail-monitor stop
//        JobFailMonitorHelper.getInstance().toStop();
//
//        // admin registry stop
//        JobRegistryMonitorHelper.getInstance().toStop();

    }


    // ---------------------- executor-client ----------------------
    private static ConcurrentMap<String, ExecutorBiz> executorBizRepository = new ConcurrentHashMap<String, ExecutorBiz>();
    public static ExecutorBiz getExecutorBiz(String address) throws Exception {
        // valid
        if (address==null || address.trim().length()==0) {
            return null;
        }

        // load-cache
        address = address.trim();
        ExecutorBiz executorBiz = executorBizRepository.get(address);
        if (executorBiz != null) {
            return executorBiz;
        }

        // set-cache
        executorBiz = new ExecutorBizClient(address, SimpleJobAdminConfig.getAdminConfig().getAccessToken());

        executorBizRepository.put(address, executorBiz);
        return executorBiz;
    }

}
