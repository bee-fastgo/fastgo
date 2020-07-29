package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 软件运行环境
 *
 * @author liko
 * @date   2020/07/29
 */
@Data
@ApiModel(value = "serverRunProfileVo", description = "软件运行环境")
public class ServerRunProfileVo{
    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;

    /**
     * 服务器IP

     */
    @ApiModelProperty(value = "服务器IP", required = true, example = "1")
    private String serverIp;

    /**
     * 软件唯一标识(参考字典)
     */
    @ApiModelProperty(value = "软件唯一标识(参考字典)", required = true, example = "1")
    private String softwareCode;

    /**
     * 运行环境名称
     */
    @ApiModelProperty(value = "运行环境名称", required = true, example = "1")
    private String softwareName;

    /**
     * 运行环境元配置
     */
    @ApiModelProperty(value = "id", required = true, example = "1")
    private String softwareConfig;

    /**
     * 运行环境Code
     */
    @ApiModelProperty(value = "运行环境Code", required = true, example = "1")
    private String runProfileCode;
}