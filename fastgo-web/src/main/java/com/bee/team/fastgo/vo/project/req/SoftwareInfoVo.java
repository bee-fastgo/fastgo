package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "softwareInfoVo",description = "软件环境信息vo")
public class SoftwareInfoVo implements Serializable {

    @ApiModelProperty(value = "软件环境ip", example = "11.10.9.8")
    @NotBlank(message = "软件环境ip不能为空")
    private String softwareServerIp;

    @ApiModelProperty(value = "软件名称", example = "测试项目")
    private String softwareName;

    @ApiModelProperty(value = "软件版本号", example = "5.0")
    @NotBlank(message = "软件版本号不能为空")
    private String version;

}
