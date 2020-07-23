package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName RemoveProjectDataReqVo
 * @Description 删除项目的某一配置项
 * @Author xqx
 * @Date 2020/7/23 14:50
 * @Version 1.0
 **/
@Data
@ApiModel(value = "removeProjectDataReqVo", description = "删除项目的某一配置项")
public class RemoveProjectDataReqVo {
    @ApiModelProperty(value = "项目的唯一标识code", example = "123131", required = true)
    @NotNull(message = "项目code不能为空")
    private String configCode;

    @ApiModelProperty(value = "软件名", example = "MySQL", required = true)
    @NotNull(message = "软件名不能为空")
    private String softName;

    @ApiModelProperty(value = "要删除的键", example = "spring.data.**", required = true)
    @NotNull(message = "键不能为空")
    private String key;
}
