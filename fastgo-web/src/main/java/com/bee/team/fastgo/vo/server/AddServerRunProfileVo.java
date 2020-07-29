package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liko
 * @description 添加运行环境
 * @date 2020/07/29
 */
@Data
@ApiModel(value = "addServerRunProfileVo", description = "添加运行环境")
public class AddServerRunProfileVo {
    /**
     * 服务器IP
     */
    @ApiModelProperty(value = "服务器IP", required = true, example = "172.22.5.248")
    @NotNull(message = "服务器IP不能为空")
    private String serverIp;
    /**
     * 运行环境名称
     */
    @ApiModelProperty(value = "运行环境名称", required = true, example = "xxx测试环境")
    @NotNull(message = "运行环境名称不能为空")
    private String softwareName;
}