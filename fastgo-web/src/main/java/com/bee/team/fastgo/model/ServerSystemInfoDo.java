package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_system_info
 *
 * @author hjs
 * @date   2020/07/27
 */
@Data
public class ServerSystemInfoDo extends LavaDo {
    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 系统版本信息
     */
    private String version;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 总计内存，G
     */
    private String totalMem;

    /**
     * core的个数(即核数)
     */
    private Integer cpuCoreNum;

    /**
     * CPU型号信息
     */
    private String cpuModel;

    /**
     * 主机状态，1正常，2下线
     */
    private Integer state;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.monitor.ServerSystemInfoBo";
    }
}