package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/7/28 15:34
 * @desc 前台项目部署vo
 **/
@Data
public class DeployFrontPorjectVo {

    @ApiModelProperty(value = "项目code",example = "TEST")
    private String projectCode;

    @ApiModelProperty(value = "项目分支名",example = "dev")
    private String branchName;

    @ApiModelProperty(value = "后台项目地址",example = "http://12.12.12.12")
    private String serviceUrl;

}
