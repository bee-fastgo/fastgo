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

    @ApiModelProperty(value = "分支名称", example = "2020-06-06")
    private String branchName;

    @ApiModelProperty(value = "项目名称", example = "2020-06-06")
    private String projectName;

    @ApiModelProperty(value = "执行人", example = "2020-06-06")
    private String user;

    @ApiModelProperty(value = "用户成员", example = "2020-06-06")
    private String gmtCreate;

    @ApiModelProperty(value = "部署日志id", example = "2020-06-06")
    private String deployLogId;

}
