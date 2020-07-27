package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hs
 * @date 2020/7/25 17:12
 * @desc 新增前台项目vo
 **/
@Data
@ApiModel(value = "insertFrontProjectVo",description = "新增前台项目vo")
public class InsertFrontProjectVo implements Serializable {

    @ApiModelProperty(value = "项目名称", example = "testProject")
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    @ApiModelProperty(value = "项目类型:1-backendTemplate,2-h5Template", example = "1")
    @NotNull(message = "项目类型不能为空")
    private Integer projectType;

    @ApiModelProperty(value = "项目描述", example = "测试项目")
    @NotBlank(message = "项目描述不能为空")
    private String projectDesc;

    @ApiModelProperty(value = "项目git地址", example = "172.22.5.222/gitlab")
    private String gitUrl;

    @ApiModelProperty(value = "运行服务器ip", example = "127.0.0.1")
    private String runServerIp;

    @ApiModelProperty(value = "项目环境名称", example = "测试环境")
    @NotBlank(message = "项目环境名称不能为空")
    private String profileName;

    @ApiModelProperty(value = "运行环境元配置",example = "{'ip':'123.123.121.111','port':1234}")
    private String runProfileConfig;

    @ApiModelProperty(value = "运行环境code", example = "TESTPROFILE")
    private String runProfileCode;

    @ApiModelProperty(value = "软件环境list", example = "[{'softwareServerIp':'127.11.11.13','softwareCode':'SOFTWARE_PROFILE', 'softwareConfig':'','softwareName':'mysql'}]")
    private List<SoftwareInfoVo> softwareInfoVos;

    @ApiModelProperty(value = "是否生成vue框架：0-不生成，1-生成", example = "1")
    private Integer isConfirm;

}
