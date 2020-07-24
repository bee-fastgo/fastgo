package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "softwareInfoVo",description = "软件环境信息vo")
public class SoftwareInfoVo implements Serializable {

    @ApiModelProperty(value = "软件环境ip", example = "测试项目")
    private String softwareServerIp;

    @ApiModelProperty(value = "软件环境code", example = "测试项目")
    private String softwareCode;

    @ApiModelProperty(value = "软件环境元配置", required = true, example = "测试项目")
    private String softwareConfig;

    @ApiModelProperty(value = "软件名称", example = "测试项目")
    private String softwareName;



}
