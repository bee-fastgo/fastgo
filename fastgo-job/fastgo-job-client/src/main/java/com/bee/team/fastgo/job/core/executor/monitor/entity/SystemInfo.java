package com.bee.team.fastgo.job.core.executor.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @desc: 查看系统信息
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
@Data
public class SystemInfo implements Serializable {

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

}
