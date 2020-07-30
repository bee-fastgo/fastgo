package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "queryProjectListVo",description = "查询项目列表vo")
public class QueryProjectListVo implements Serializable {

    @ApiModelProperty(value = "开始页", example = "1", required = true)
    @NotNull(message = "分页参数不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量", example = "15", required = true)
    @NotNull(message = "每页数量不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "项目名称", example = "project", required = true)
    private String projectName;

    @ApiModelProperty(value = "项目状态", example = "1", required = true)
    private String projectStatus;

}
