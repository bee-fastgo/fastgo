package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author liko
 * @description MyBatis Generator 自动创建,对应数据表为：t_server
 * @date 2020/07/20
 */
@Data
@ApiModel(value = "addServerVo", description = "修改服务对象")
public class ServerVo {
    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;

    /**
     * 服务器名称
     */
    @ApiModelProperty(value = "服务器名称", required = true, example = "xxx测试环境")
    private String serverName;


    /**
     * 服务器IP
     */
    @ApiModelProperty(value = "服务器IP", required = true, example = "172.22.5.243")
    private String serverIp;

    /**
     * ssh端口
     */
    @ApiModelProperty(value = "ssh端口", required = true, example = "22")
    private Integer sshPort;

    /**
     * ssh用户
     */
    @ApiModelProperty(value = "ssh用户", required = true, example = "root")
    private String sshUser;

    /**
     * ssh密码
     */
    @ApiModelProperty(value = "ssh密码", required = true, example = "123456")
    private String sshPassword;

    /**
     * 1:未连接
     * 2:已连接
     */
    @ApiModelProperty(value = "ssh密码 1:未连接 2:已连接", required = true, example = "1")
    private String serverStatus;

    /**
     * 1:手动注册
     * 2:自动注册
     */
    @ApiModelProperty(value = "ssh密码 1:手动注册 2:自动注册", required = true, example = "1")
    private String type;

    /**
     * 服务调用token
     */
    @ApiModelProperty(value = "服务调用token", required = true, example = "fw52hsxnd")
    private String serviceToken;
}