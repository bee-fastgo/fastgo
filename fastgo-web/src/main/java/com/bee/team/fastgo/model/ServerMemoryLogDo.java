package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_memory_log
 *
 * @author hjs
 * @date   2020/07/24
 */
@Data
public class ServerMemoryLogDo extends LavaDo {
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

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.monitor.ServerMemoryLogBo";
    }
}