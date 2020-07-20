package com.bee.team.fastgo.job.core.util;

/**
 * sharding vo
 *
 * @author luke 2017-07-25 21:26:38
 */
public class ShardingUtil {

    private static InheritableThreadLocal<String> contextHolder = new InheritableThreadLocal<String>();


    public static void setShardingVo(String params) {
        contextHolder.set(params);
    }

    public static String getShardingVo() {
        return contextHolder.get();
    }

}
