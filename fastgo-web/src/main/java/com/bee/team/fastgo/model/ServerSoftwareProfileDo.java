package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_software_profile
 *
 * @author liko
 * @date   2020/07/27
 */
@Data
public class ServerSoftwareProfileDo extends LavaDo {
    /**
     * 服务器IP

     */
    private String serverIp;

    /**
     * 软件唯一标识
     */
    private String softwareCode;

    /**
     * 字典名
     */
    private String softwareName;

    /**
     * 软件环境元配置
     */
    private String softwareConfig;

    /**
     * 版本
     */
    private String version;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.ServerSoftwareProfileBo";
    }
}