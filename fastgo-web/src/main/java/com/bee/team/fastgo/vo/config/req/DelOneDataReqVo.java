package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/3 13:45
 * @desc 删除项目配置项的参数类
 **/
@Data
@ApiModel(value = "delOneDataReqVo", description = "删除项目配置项的参数类")
public class DelOneDataReqVo {
    @ApiModelProperty(value = "项目code", example = "454545", required = true)
    @NotNull(message = "项目code不能为空")
    private String projectCode;

    @ApiModelProperty(value = "软件名", example = "mysql", required = true)
    @NotNull(message = "软件名不能为空")
    private String softName;

    @ApiModelProperty(value = "配置项的key", example = "spring.data.mongo.**")
    private String key;
}
