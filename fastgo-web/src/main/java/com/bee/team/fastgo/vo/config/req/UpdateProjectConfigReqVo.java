package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName UpdateProjectConfigReqVo
 * @Description 修改项目配置的参数类
 * @Author xqx
 * @Date 2020/7/22 15:32
 * @Version 1.0
 **/
@Data
@ApiModel(value = "updateProjectConfigReqVo", description = "修改项目配置的参数类")
public class UpdateProjectConfigReqVo {
    @ApiModelProperty(value = "项目code", example = "20121221545", required = true)
    @NotNull(message = "项目标识不能为空")
    private String projectCode;

    @ApiModelProperty(value = "修改项", required = true)
    private List<SoftReqVo> softReqVoList;

}
