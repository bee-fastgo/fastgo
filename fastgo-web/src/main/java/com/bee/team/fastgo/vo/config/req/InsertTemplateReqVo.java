package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName InsertTemplateReqVo
 * @Description 新增的模板参数类
 * @Author xqx
 * @Date 2020/7/22 11:11
 * @Version 1.0
 **/
@Data
@ApiModel(value = "insertTemplateReqVo", description = "新增的模板参数类")
public class InsertTemplateReqVo {
    @ApiModelProperty(value = "模板名字", example = "mysql", required = true)
//    @NotBlank(message = "模板名字不能为空")
    private String templateName;
    @ApiModelProperty(value = "模板描述", example = "mysql数据库", required = true)
//    @NotBlank(message = "描述不能为空")
    private String description;
    @ApiModelProperty(value = "模板参数", required = true)
//    @NotEmpty(message = "参数不能为空")
    private List<MapReqVo> list;
}
