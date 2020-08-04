package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/4 9:41
 * @desc 分页参数
 **/
@Data
@ApiModel(value = "pageReqVo", description = "分页参数")
public class PageReqVo {
    @ApiModelProperty(value = "开始页", example = "1", required = true)
    @NotNull(message = "开始页不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数", example = "10", required = true)
    @NotNull(message = "每页数不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "查询条件", example = "admin")
    private String condition;
}
