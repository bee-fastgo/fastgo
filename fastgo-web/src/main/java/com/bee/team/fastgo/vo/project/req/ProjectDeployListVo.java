package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hs
 * @date 2020/8/6 15:02
 * @desc 项目部署列表vo
 **/

@Data
@ApiModel(value = "projectDeployListVo",description = "项目部署列表vo")
public class ProjectDeployListVo {

    @ApiModelProperty(value = "开始页", example = "1", required = true)
    @NotNull(message = "分页参数不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量", example = "15", required = true)
    @NotNull(message = "每页数量不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "项目code", example = "asdasdasda", required = true)
    @NotBlank(message = "项目code不能为空")
    private String projectCode;

}
