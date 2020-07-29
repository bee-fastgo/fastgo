package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "insertBackProjectProfileVo",description = "新增项目环境vo")
public class InsertBackProjectProfileVo implements Serializable {

    @ApiModelProperty(value = "项目code", example = "TEST")
    @NotBlank(message = "项目code不能为空")
    private String projectCode;

    @ApiModelProperty(value = "项目名称", example = "TEST")
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    @ApiModelProperty(value = "项目描述", example = "TEST")
    @NotBlank(message = "项目描述不能为空")
    private String projectDesc;

    @ApiModelProperty(value = "分支名称", example = "dev")
    @NotBlank(message = "项目分支名称不能为空")
    private String branchName;

    @ApiModelProperty(value = "运行服务器ip", example = "127.0.0.1")
    @NotBlank(message = "运行服务器ip不能为空")
    private String runServerIp;

    @ApiModelProperty(value = "项目环境名称", example = "测试环境")
    @NotBlank(message = "项目环境名称不能为空")
    private String profileName;

    @ApiModelProperty(value = "运行服务器端口", example = "65535")
    @NotNull(message = "运行服务器端口不能为空")
    private String runServerPort;

    @ApiModelProperty(value = "软件环境list", example = "[{'softwareServerIp':'127.11.11.13','softwareName':'mysql','version':'5.0.0'}]")
    private List<SoftwareInfoVo> softwareInfoVos;

}
