package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xqx
 * @date 2020/7/29 18:22
 * @desc 资源信息类
 **/
@Data
@ApiModel(value = "serverSourceResVo", description = "资源信息类")
public class ServerSourceResVo {
    @ApiModelProperty(value = "资源名称", example = "mysql")
    private String sourceName;

    @ApiModelProperty(value = "资源下载地址", example = "http://172.22.5.248:9999/data/soft/a.jpg")
    private String sourceDownUrl;

    @ApiModelProperty(value = "资源配置文件下载地址", example = "123")
    private String sourceConfigDownUrl;

    @ApiModelProperty(value = "资源版本", example = "5.7")
    private String sourceVersion;

    @ApiModelProperty(value = "软件包元配置")
    private String sourceConfig;

    @ApiModelProperty(value = "字典名", example = "mysql")
    private String softwareName;

    @ApiModelProperty(value = "标识", example = "454545")
    private String sourceCode;
}
