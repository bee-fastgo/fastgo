package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName MapReqVo
 * @Description 键值对
 * @Author xqx
 * @Date 2020/7/22 11:29
 * @Version 1.0
 **/
@Data
@ApiModel(value = "mapReqVo", description = "键值对")
public class MapReqVo {
    @ApiModelProperty(value = "键", example = "spring.data.mongo.port", required = true)
    @NotNull(message = "参数不能为空")
    private String key;

    @ApiModelProperty(value = "值", example = "27017", required = true)
    @NotNull(message = "参数不能为空")
    private String value;
}
