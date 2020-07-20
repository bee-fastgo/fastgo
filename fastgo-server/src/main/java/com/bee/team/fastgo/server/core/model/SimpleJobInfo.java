package com.bee.team.fastgo.server.core.model;

import lombok.Data;

import java.util.Date;

/**
 * simple-job info
 *
 * @author luke  2016-1-12 18:25:49
 */
@Data
public class SimpleJobInfo {
    /**
     * jobId
     **/
    private Long id;

    /**
     * 线程Id
     **/
    private Long threadId;
    /**
     * 触发时间
     **/
    private Date addTime;
    /**
     * 触发回调时间
     **/
    private Date updateTime;
    /**
     * 执行器，任务Handler名称
     **/
    private String executorHandler;
    /**
     * 执行器，执行参数
     **/
    private String executorParam;
    /**
     * 阻塞处理策略
     **/
    private String executorBlockStrategy;
    /**
     * 任务执行超时时间，单位秒
     **/
    private int executorTimeout;
    /**
     * 失败重试次数
     **/
    private int executorFailRetryCount;
    /**
     * GLUE类型	#com.bee.team.fastgo.job.core.glue.GlueTypeEnum
     **/
    private String glueType;
    /**
     * GLUE源代码
     **/
    private String glueSource;
}
