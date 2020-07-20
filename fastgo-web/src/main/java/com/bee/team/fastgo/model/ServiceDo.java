package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server
 *
 * @author liko
 * @date   2020/07/20
 */
@Data
public class ServiceDo extends LavaDo {
    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * ssh端口
     */
    private Integer sshPort;

    /**
     * ssh用户
     */
    private String sshUser;

    /**
     * ssh密码
     */
    private String sshPassword;

    /**
     * 1:未连接
2:已连接
     */
    private String serverStatus;

    /**
     * 1:手动注册
2:自动注册
     */
    private String type;

    /**
     * 服务调用token
     */
    private String clientName;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.ServiceBo";
    }
}