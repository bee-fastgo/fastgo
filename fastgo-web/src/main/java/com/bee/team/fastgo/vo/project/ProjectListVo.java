package com.bee.team.fastgo.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "projectListVo",description = "项目展示vo")
public class ProjectListVo implements Serializable {

    @ApiModelProperty(value = "主键id", required = true, example = "11")
    private Integer id;

    @ApiModelProperty(value = "项目名称", required = true, example = "testProject")
    private String projectName;

    @ApiModelProperty(value = "项目code", required = true, example = "testProject")
    private String projectCode;

    @ApiModelProperty(value = "项目描述", required = true, example = "测试项目")
    private String projectDesc;

    @ApiModelProperty(value = "项目地址", required = true, example = "http://127.0.0.1/fastgo")
    private String gitUrl;

    @ApiModelProperty(value = "项目状态：1.项目创建中，2.项目创建成功，3.项目部署中 4.项目部署完成，5-软件环境部署成功，6-运行环境部署成功，7-项目部署失败", required = true, example = "1")
    private Integer projectStatus;

    @ApiModelProperty(value = "项目包名", required = true, example = "com.bee.fastgo")
    private String packageName;

    @ApiModelProperty(value = "自动部署", required = true, example = "0")
    private Integer autoDeploy;

    @ApiModelProperty(value = "访问地址", required = true, example = "172.22.5.22:9999")
    private String accessAddr;

}
