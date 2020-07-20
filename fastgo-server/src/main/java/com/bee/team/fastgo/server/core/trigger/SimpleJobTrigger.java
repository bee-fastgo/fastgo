package com.bee.team.fastgo.server.core.trigger;


import com.bee.team.fastgo.job.core.biz.ExecutorBiz;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.biz.model.TriggerParam;
import com.bee.team.fastgo.job.core.util.IpUtil;
import com.bee.team.fastgo.job.core.util.ThrowableUtil;
import com.bee.team.fastgo.server.core.model.SimpleJobAddress;
import com.bee.team.fastgo.server.core.model.SimpleJobInfo;
import com.bee.team.fastgo.server.core.model.SimpleJobLog;
import com.bee.team.fastgo.server.core.scheduler.SimpleJobScheduler;
import com.spring.simple.development.support.utils.PrimaryKeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * simple-job trigger
 *
 * @author luke
 * @date 17/7/13
 */
public class SimpleJobTrigger {
    private static Logger logger = LoggerFactory.getLogger(SimpleJobTrigger.class);

    /**
     * @return java.util.List<com.bee.team.fastgo.server.core.model.SimpleJobLog>
     * @Author luke
     * @Description trigger job
     * @Date 14:41 2020/7/20 0020
     * @Param [jobInfo, triggerType, executorShardingParam, simpleJobAddress]
     **/
    public static List<SimpleJobLog> trigger(SimpleJobInfo jobInfo,
                                             TriggerTypeEnum triggerType,
                                             SimpleJobAddress simpleJobAddress) {


        // cover addressList
        simpleJobAddress.setAddressList(simpleJobAddress.getAddressList().trim());
        List<SimpleJobLog> simpleJobLogs = processTrigger(simpleJobAddress, jobInfo, triggerType);
        return simpleJobLogs;
    }

    /**
     * @param simpleJobAddress job group, registry list may be empty
     * @param jobInfo
     * @param triggerType
     */
    private static List<SimpleJobLog> processTrigger(SimpleJobAddress simpleJobAddress, SimpleJobInfo jobInfo, TriggerTypeEnum triggerType) {

        int finalFailRetryCount = jobInfo.getExecutorFailRetryCount();

        List<String> registryList = simpleJobAddress.getRegistryList();
        List<SimpleJobLog> simpleJobLogs = new ArrayList<>();
        for (String address : registryList) {
            // 1、save log-id
            SimpleJobLog jobLog = new SimpleJobLog();
            jobLog.setId(jobInfo.getId());
            jobLog.setTriggerTime(new Date());
            logger.debug(">>>>>>>>>>> simple-job trigger start, jobId:{}", jobLog.getId());

            // 2、init trigger-param
            TriggerParam triggerParam = new TriggerParam();
            triggerParam.setJobId(jobInfo.getThreadId().intValue());
            triggerParam.setExecutorHandler(jobInfo.getExecutorHandler());
            triggerParam.setExecutorParams(jobInfo.getExecutorParam());
            triggerParam.setExecutorBlockStrategy(jobInfo.getExecutorBlockStrategy());
            triggerParam.setExecutorTimeout(jobInfo.getExecutorTimeout());
            triggerParam.setLogId(jobLog.getId());
            triggerParam.setLogDateTime(jobLog.getTriggerTime().getTime());
            triggerParam.setGlueType(jobInfo.getGlueType());
            triggerParam.setGlueSource(jobInfo.getGlueSource());
            triggerParam.setGlueUpdateTime(System.currentTimeMillis());

            jobLog.setExecutorAddress(address);
            jobLog.setThreadId(jobInfo.getThreadId());
            // 3、init address
            address = "http://" + address + ":" + "9999/";
            // 4、trigger remote executor
            ReturnT<String> triggerResult = null;
            if (address != null) {
                triggerResult = runExecutor(triggerParam, address, jobLog);
            } else {
                triggerResult = new ReturnT<String>(ReturnT.FAIL_CODE, null);
            }

            // 5、collection trigger info
            StringBuffer triggerMsgSb = new StringBuffer();
            triggerMsgSb.append("任务触发类型").append("：").append(triggerType.getTitle());
            triggerMsgSb.append("<br>").append("调度机器").append("：").append(IpUtil.getIp());
            triggerMsgSb.append("<br>").append("执行器-注册方式").append("：")
                    .append((simpleJobAddress.getAddressType() == 0) ? "自动注册" : "手动录入");
            triggerMsgSb.append("<br>").append("执行器-地址列表").append("：").append(simpleJobAddress.getRegistryList());
            triggerMsgSb.append("<br>").append("路由策略").append("：").append("单机");

            triggerMsgSb.append("<br>").append("任务超时时间").append("：").append(jobInfo.getExecutorTimeout());
            triggerMsgSb.append("<br>").append("失败重试次数").append("：").append(finalFailRetryCount);

            triggerMsgSb.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>" + "触发调度" + "<<<<<<<<<<< </span><br>")
                    .append("<br><br>").append(triggerResult.getMsg() != null ? triggerResult.getMsg() : "");

            // 6、save log trigger-info
            jobLog.setExecutorHandler(jobInfo.getExecutorHandler());
            jobLog.setExecutorParam(jobInfo.getExecutorParam());
            jobLog.setExecutorFailRetryCount(finalFailRetryCount);
            jobLog.setTriggerCode(triggerResult.getCode());
            jobLog.setTriggerWebMsg(triggerMsgSb.toString());
            logger.debug(">>>>>>>>>>> simple-job trigger end, jobId:{}", jobLog.getId());
            simpleJobLogs.add(jobLog);
        }
        return simpleJobLogs;
    }

    /**
     * run executor
     *
     * @param triggerParam
     * @param address
     * @return
     */
    public static ReturnT<String> runExecutor(TriggerParam triggerParam, String address, SimpleJobLog jobLog) {
        ReturnT<String> runResult = null;
        try {
            ExecutorBiz executorBiz = SimpleJobScheduler.getExecutorBiz(address);
            runResult = executorBiz.run(triggerParam);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>> simple-job trigger error, please check if the executor[{}] is running.", address, e);
            runResult = new ReturnT<String>(ReturnT.FAIL_CODE, ThrowableUtil.toString(e));
        }

        StringBuffer runResultSB = new StringBuffer("触发调度" + "：");
        runResultSB.append("<br>address：").append(address);
        runResultSB.append("<br>code：").append(runResult.getCode());
        runResultSB.append("<br>msg：").append(runResult.getMsg());
        jobLog.setTriggerMsg(runResult.getMsg());
        runResult.setMsg(runResultSB.toString());
        return runResult;
    }

}
