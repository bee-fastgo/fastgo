package com.bee.team.fastgo.job.core.executor.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc: uptime查看系统负载状态
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
@Data
public class SysLoadState implements Serializable {

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 1分钟之前到现在的负载
     */
    private Double oneLoad;

    /**
     * 5分钟之前到现在的负载
     */
    private Double fiveLoad;

    /**
     * 15分钟之前到现在的负载
     */
    private Double fifteenLoad;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
