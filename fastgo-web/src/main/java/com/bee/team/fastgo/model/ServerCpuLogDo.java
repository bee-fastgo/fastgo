package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_cpu_log
 *
 * @author hjs
 * @date   2020/07/24
 */
@Data
public class ServerCpuLogDo extends LavaDo {
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

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.monitor.ServerCpuLogBo";
    }
}