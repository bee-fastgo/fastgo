package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "updateProjectStatusVo",description = "修改项目状态vo")
public class UpdateProjectStatusVo implements Serializable {

    @ApiModelProperty(value = "code", example = "TEST")
    @NotBlank(message = "code不能为空")
    private String code;

    @ApiModelProperty(value = "请求类型", example = "1-运行环境，2-软件环境")
    @NotNull(message = "请求类型不能为空")
    private Integer type;

}
