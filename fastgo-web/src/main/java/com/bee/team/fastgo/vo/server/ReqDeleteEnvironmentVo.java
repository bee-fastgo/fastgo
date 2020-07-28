package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 请求删除环境实体
 **/
@Data
@ApiModel(value = "ReqDeleteEnvironmentVo", description = "请求删除环境实体")
public class ReqDeleteEnvironmentVo {

    @ApiModelProperty(value = "环境code", example = "safabjfk", required = true)
    @NotNull(message = "环境code不能为空")
    private String softwareCode;
}
