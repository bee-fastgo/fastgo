package com.bee.team.fastgo.job.core.executor.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc: app状态监控
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
@Data
public class AppState implements Serializable {


    /**
     * 应用信息ID
     */
    private String appInfoId;


    /**
     * %CPU
     */
    private Double cpuPer;

    /**
     * %MEM
     */
    private Double memPer;

    /**
     * 添加时间
     * MM-dd hh:mm:ss
     */
    private String dateStr;

    /**
     * 创建时间
     */
    private Date createTime;
}
