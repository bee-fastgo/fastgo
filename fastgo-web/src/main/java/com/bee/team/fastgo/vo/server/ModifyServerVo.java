package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liko
 * @description MyBatis Generator 自动创建,对应数据表为：t_server
 * @date 2020/07/20
 */
@Data
@ApiModel(value = "addServerVo", description = "修改服务对象")
public class ModifyServerVo {
    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true, example = "1")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 服务器名称
     */
    @ApiModelProperty(value = "服务器名称", required = true, example = "xxx测试环境")
    @NotNull(message = "服务器名称不能为空")
    private String serverName;


    /**
     * 服务器IP
     */
    @ApiModelProperty(value = "服务器IP", required = true, example = "172.22.5.243")
    @NotNull(message = "服务器IP不能为空")
    private String serverIp;

    /**
     * ssh端口
     */
    @ApiModelProperty(value = "ssh端口", required = true, example = "22")
    @NotNull(message = "ssh端口不能为空")
    private Integer sshPort;

    /**
     * ssh用户
     */
    @ApiModelProperty(value = "ssh用户", required = true, example = "root")
    @NotNull(message = "ssh用户不能为空")
    private String sshUser;

    /**
     * ssh密码
     */
    @ApiModelProperty(value = "ssh密码", required = true, example = "123456")
    @NotNull(message = "ssh密码不能为空")
    private String sshPassword;
}