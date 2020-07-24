package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "insertBackProjectProfileVo",description = "新增项目环境vo")
public class InsertBackProjectProfileVo implements Serializable {

    @ApiModelProperty(value = "项目code", example = "TEST")
    private String projectCode;

    @ApiModelProperty(value = "项目名称", example = "TEST")
    private String projectName;

    @ApiModelProperty(value = "项目描述", example = "TEST")
    private String projectDesc;

    @ApiModelProperty(value = "分支名称", example = "dev")
    private String branchName;

    @ApiModelProperty(value = "运行服务器ip", example = "127.0.0.1")
    private String runServerIp;

    @ApiModelProperty(value = "项目环境名称", example = "测试环境")
    private String profileName;

    @ApiModelProperty(value = "运行环境元配置",example = "xxx")
    private String runProfileConfig;

    @ApiModelProperty(value = "运行环境code", example = "测试项目")
    private String runProfileCode;

    @ApiModelProperty(value = "软件环境list", example = "xxx")
    private List<SoftwareInfoVo> softwareInfoVos;

}
