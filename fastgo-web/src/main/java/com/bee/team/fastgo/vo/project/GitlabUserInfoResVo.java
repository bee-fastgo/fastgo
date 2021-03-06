package com.bee.team.fastgo.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/8/5 15:12
 * @desc gitlab 用户信息
 **/
@Data
@ApiModel(value = "gitlabUserInfoResVo",description = "gitlab用户信息vo")
public class GitlabUserInfoResVo {

    @ApiModelProperty(value = "主键id",example = "1")
    private Integer id;

    @ApiModelProperty(value = "用户名",example = "test")
    private String gitlabUserName;

    @ApiModelProperty(value = "真实姓名",example = "test")
    private String gitlabName;

    @ApiModelProperty(value = "邮箱地址",example = "163163@163.com")
    private String gitlabEmail;

    @ApiModelProperty(value = "状态: 1-active，2-blocked",example = "1")
    private Integer status;

}
