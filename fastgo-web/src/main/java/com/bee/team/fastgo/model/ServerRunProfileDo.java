package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_run_profile
 *
 * @author liko
 * @date   2020/07/29
 */
@Data
public class ServerRunProfileDo extends LavaDo {
    /**
     * 服务器IP

     */
    private String serverIp;

    /**
     * 软件唯一标识(参考字典)
     */
    private String softwareCode;

    /**
     * 运行环境名称
     */
    private String softwareName;

    /**
     * 运行环境元配置
     */
    private String softwareConfig;

    /**
     * 运行环境Code
     */
    private String runProfileCode;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.ServerRunProfileBo";
    }
}