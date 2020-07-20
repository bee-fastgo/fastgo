package com.bee.team.fastgo.vo.server;

import lombok.Data;

/**
 * @author liko
 * @description MyBatis Generator 自动创建,对应数据表为：t_server
 * @date 2020/07/20
 */
@Data
public class ModifyServiceVo {
    /**
     * id
     */
    private Long id;
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
}