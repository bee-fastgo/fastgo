package com.bee.team.fastgo.hander;

import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luke
 * @desc 推送助手
 * @date 2020-07-27
 **/
public class JobHandler {
    public static Map<String, ? super JobPush> jobMap = new ConcurrentHashMap<>();

    public static void push(HandleCallbackParam handleCallbackParam) {
        boolean b = jobMap.containsKey(String.valueOf(handleCallbackParam.getLogId()));
        if (b) {
            JobPush jobPush = (JobPush) jobMap.get(String.valueOf(handleCallbackParam.getLogId()));
            jobPush.receiveMessage(handleCallbackParam);
            jobMap.remove(String.valueOf(handleCallbackParam.getLogId()));
        }
    }
}
