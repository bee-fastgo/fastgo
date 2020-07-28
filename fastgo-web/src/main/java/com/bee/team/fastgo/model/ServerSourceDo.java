package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_source
 *
 * @author liko
 * @date   2020/07/28
 */
@Data
public class ServerSourceDo extends LavaDo {
    /**
     * 资源名称
     */
    private String sourceName;

    /**
     * 资源下载地址
     */
    private String sourceDownUrl;

    /**
     * 资源配置文件下载地址
     */
    private String sourceConfigDownUrl;

    /**
     * 资源版本
     */
    private String sourceVersion;

    /**
     * 资源标识
     */
    private String sourceCode;

    /**
     * 软件包元配置
     */
    private String sourceConfig;

    /**
     * 字典名
     */
    private String softwareName;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.ServerSourceBo";
    }
}