package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName ListProjectConfigsReqVo
 * @Description 项目配置列表参数类
 * @Author xqx
 * @Date 2020/7/22 10:11
 * @Version 1.0
 **/
@Data
@ApiModel(value = "listProjectConfigsReqVo", description = "项目配置列表参数类")
public class ListProjectConfigsReqVo {
    @ApiModelProperty(value = "开始页", example = "1", required = true)
    @NotNull(message = "分页参数不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量", example = "15", required = true)
    @NotNull(message = "每页数量不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "项目名", example = "阿里巴巴")
    private String projectName;
}
