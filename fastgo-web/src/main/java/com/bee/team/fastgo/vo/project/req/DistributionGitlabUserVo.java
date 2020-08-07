package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hs
 * @date 2020/8/7 18:16
 * @desc 分配gitlab user
 **/
@Data
@ApiModel(value = "distributionGitlabUserVo",description = "分配gitlabUserVo ")
public class DistributionGitlabUserVo {

    @NotNull(message = "主键id不能为空")
    @ApiModelProperty(value = "gitlabUser主键",example = "1")
    private Integer id;

    @NotNull(message = "userId不能为空")
    @ApiModelProperty(value = "用户id",example = "1")
    private Integer userId;

}
