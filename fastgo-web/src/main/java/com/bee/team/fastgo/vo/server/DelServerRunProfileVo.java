package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liko
 * @description 销毁运行环境
 * @date 2020/07/29
 */
@Data
@ApiModel(value = "delServerRunProfileVo", description = "销毁运行环境")
public class DelServerRunProfileVo {
    /**
     * 运行环境名称
     */
    @ApiModelProperty(value = "运行环境Code", required = true, example = "x001")
    @NotNull(message = "运行环境Code不能为空")
    private String runProfileCode;
}