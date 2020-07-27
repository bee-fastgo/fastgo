package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_net_io_log
 *
 * @author hjs
 * @date   2020/07/27
 */
@Data
public class ServerNetIOLogDo extends LavaDo {
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

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.monitor.ServerNetIOLogBo";
    }
}