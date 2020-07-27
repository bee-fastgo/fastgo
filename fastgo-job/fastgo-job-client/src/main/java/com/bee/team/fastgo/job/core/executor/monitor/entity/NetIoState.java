package com.bee.team.fastgo.job.core.executor.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc: 网络设备的吞吐率
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
@Data
public class NetIoState implements Serializable {

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 每秒钟接收的数据包,rxpck/s
     */
    private String rxpck;

    /**
     * 每秒钟发送的数据包,txpck/s
     */
    private String txpck;

    /**
     * 每秒钟接收的KB数,rxkB/s
     */
    private String rxbyt;

    /**
     * 每秒钟发送的KB数,txkB/s
     */
    private String txbyt;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
