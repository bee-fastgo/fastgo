package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/7/28 18:02
 * @desc 添加软件资源的参数类
 **/
@ApiModel(value = "addSoftResourceResVo", description = "添加软件资源的参数类")
@Data
public class ResAddSoftResourceVo {
    @ApiModelProperty(value = "资源名称", example = "mysql", required = true)
    @NotNull(message = "资源名称不能为空")
    private String sourceName;

    @ApiModelProperty(value = "资源下载地址", example = "http://172.22.5.248:9999/data/soft/a.jpg", required = true)
    @NotNull(message = "资源下载地址不能为空")
    private String sourceDownUrl;

    @ApiModelProperty(value = "资源配置文件下载地址", example = "123", required = true)
    @NotNull(message = "资源配置文件下载地址不能为空")
    private String sourceConfigDownUrl;

    @ApiModelProperty(value = "资源版本", example = "5.7", required = true)
    @NotNull(message = "资源版本不能为空")
    private String sourceVersion;

    @ApiModelProperty(value = "资源标识", example = "1234564646", required = true)
    @NotNull(message = "资源标识不能为空")
    private String sourceCode;

    @ApiModelProperty(value = "软件包元配置", example = "12", required = true)
    @NotNull(message = "软件包元配置不能为空")
    private String sourceConfig;

    @ApiModelProperty(value = "字典名", example = "mysql", required = true)
    @NotNull(message = "字典名不能为空")
    private String softwareName;
}
