package com.bee.team.fastgo.job.core.executor.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc: 进程信息
 * @auth: hjs
 * @date: 2020-07-21
 */
@Data
public class AppInfo implements Serializable {

    /**
     * host名称
     */
    private String hostname;

    /**
     * 应用进程ID
     */
    private String appPid;

    /**
     * 应用进程名称
     */
    private String appName;

    /**
     *内存使用M
     */
    private Double memPer;

    /**
     * cpu使用率
     */
    private Double cpuPer;


    /**
     * 进程获取途径，1进程id号，2进程pid文件
     */
    private String appType;

    /**
     * 进程状态，1正常，2下线
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createTime;
}
