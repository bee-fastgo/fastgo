package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName UpdateTemplateReqVo
 * @Description 修改配置的参数类
 * @Author xqx
 * @Date 2020/7/22 14:39
 * @Version 1.0
 **/
@Data
@ApiModel(value = "updateTemplateReqVo", description = "修改配置的参数类")
public class UpdateTemplateReqVo {
    @ApiModelProperty(value = "code", example = "2013145666", required = true)
    @NotNull(message = "code不能为空")
    private String code;

    @ApiModelProperty(value = "要修改的参数信息", required = true)
    private List<MapReqVo> mapReqVos;
}
