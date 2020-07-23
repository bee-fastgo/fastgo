package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName RemoveTemplateDataReqVo
 * @Description 删除模板的配置项的参数类
 * @Author xqx
 * @Date 2020/7/23 16:28
 * @Version 1.0
 **/
@Data
@ApiModel(value = "removeTemplateDataReqVo", description = "删除模板的配置项的参数类")
public class RemoveTemplateDataReqVo {
    @ApiModelProperty(value = "模板的标识", example = "45644646", required = true)
    @NotNull(message = "模板code不能为空")
    private String code;

    @ApiModelProperty(value = "要删除的key", example = "spring.data.**", required = true)
    @NotNull(message = "key不能为空")
    private String key;
}
