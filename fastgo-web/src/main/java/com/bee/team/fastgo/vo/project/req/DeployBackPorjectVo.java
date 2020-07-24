package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "deployBackPorjectVo",description = "部署后台项目vo")
public class DeployBackPorjectVo implements Serializable {

    @ApiModelProperty(value = "项目code",example = "TEST")
    private String projectCode;

    @ApiModelProperty(value = "项目分支名",example = "dev")
    private String branchName;

}
