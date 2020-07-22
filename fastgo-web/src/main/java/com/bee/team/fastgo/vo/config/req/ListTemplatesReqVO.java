package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName ListTemplatesReqVO
 * @Description 模板列表参数类
 * @Author xqx
 * @Date 2020/7/22 13:44
 * @Version 1.0
 **/
@Data
@ApiModel(value = "listTemplatesReqVO", description = "模板列表参数类")
public class ListTemplatesReqVO {
    @ApiModelProperty(value = "页数", example = "1", required = true)
    @NotNull(message = "分页参数不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量", example = "10", required = true)
    @NotNull(message = "分页参数不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "模板名", example = "mysql")
    private String name;
}
