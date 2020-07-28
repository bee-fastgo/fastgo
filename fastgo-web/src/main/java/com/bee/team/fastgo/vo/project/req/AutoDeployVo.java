package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/7/28 16:31
 * @desc 自动部署开关vo
 **/

@Data
@ApiModel(value = "autoDeployVo",description = "自动部署项目vo")
public class AutoDeployVo {

    @ApiModelProperty(value = "项目code",example = "TEST")
    private String projectCode;

    @ApiModelProperty(value = "项目自动部署：0-关，1-开",example = "TEST")
    private Integer autoDeploy;

}
