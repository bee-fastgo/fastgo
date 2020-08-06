package com.bee.team.fastgo.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/8/5 18:24
 * @desc 项目部署记录
 **/

@Data
@ApiModel(value = "projectDeployResVo",description = "项目部署记录vo")
public class ProjectDeployResVo {

    @ApiModelProperty(value = "分支名称", example = "master")
    private String branchName;

    @ApiModelProperty(value = "项目名称", example = "test-project")
    private String projectName;

    @ApiModelProperty(value = "执行人", example = "test")
    private String user;

    @ApiModelProperty(value = "部署时间", example = "2020-06-06")
    private String gmtCreate;

    @ApiModelProperty(value = "部署日志id", example = "sasdasd")
    private String deployLogId;

    @ApiModelProperty(value = "部署状态：3-项目部署中；4-项目部署完成；7-项目部署失败", example = "4")
    private Integer projectDeployStatus;

}
