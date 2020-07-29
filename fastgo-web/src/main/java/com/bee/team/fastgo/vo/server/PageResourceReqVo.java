package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/7/29 18:05
 * @desc 获取资源列表的分页参数
 **/
@Data
@ApiModel(value = "pageResourceReqVo", description = "获取资源列表的分页参数")
public class PageResourceReqVo {
    @ApiModelProperty(value = "跳转页", example = "1", required = true)
    @NotNull(message = "跳转页不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示条数", example = "10", required = true)
    @NotNull(message = "每页显示条数不能为空")
    private Integer pageSize;
}
