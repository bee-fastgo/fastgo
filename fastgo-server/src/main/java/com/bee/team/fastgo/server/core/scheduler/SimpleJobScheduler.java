package com.bee.team.fastgo.server.core.scheduler;

import com.bee.team.fastgo.job.core.biz.ExecutorBiz;
import com.bee.team.fastgo.job.core.biz.client.ExecutorBizClient;
import com.bee.team.fastgo.server.core.conf.SimpleJobAdminConfig;
import com.bee.team.fastgo.server.core.thread.JobTriggerPoolHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author luke 2018-10-28 00:18:17
 */

public class SimpleJobScheduler {
    private static final Logger logger = LoggerFactory.getLogger(SimpleJobScheduler.class);


    public void init() throws Exception {

        // admin trigger pool start
        JobTriggerPoolHelper.toStart();

        logger.info(">>>>>>>>> init simple-job admin success.");
    }


    public void destroy() throws Exception {

        // admin trigger pool stop
        JobTriggerPoolHelper.toStop();
    }


    // ---------------------- executor-client ----------------------
    private static ConcurrentMap<String, ExecutorBiz> executorBizRepository = new ConcurrentHashMap<String, ExecutorBiz>();

    public static ExecutorBiz getExecutorBiz(String address) throws Exception {
        // valid
        if (address == null || address.trim().length() == 0) {
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
