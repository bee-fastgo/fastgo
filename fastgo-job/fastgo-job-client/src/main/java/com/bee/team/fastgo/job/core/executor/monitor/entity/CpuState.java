package com.bee.team.fastgo.job.core.executor.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc: 查看CPU使用情况
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
@Data
public class CpuState implements Serializable {

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * cpu使用率
     */
    private Double cpuUse;

    /**
     * 当前空闲率
     */
    private Double cpuIdle;

    /**
     * cpu当前等待率
     */
    private Double cpuIoWait;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
