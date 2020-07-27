package com.bee.team.fastgo.job.core.executor.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc: 查看内存使用情况
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
@Data
public class MemoryState implements Serializable {
    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 总计内存M
     */
    private Double memTotal;

    /**
     * 已使用多少M
     */
    private Double memUsed;

    /**
     * 未使用M
     */
    private Double memFree;

    /**
     * 已使用百分比%
     */
    private String memUsePer;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
