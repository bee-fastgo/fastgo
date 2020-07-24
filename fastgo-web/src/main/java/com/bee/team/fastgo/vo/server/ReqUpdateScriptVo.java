package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/24 11:47
 * @ClassName ReqUpdateScriptVo
 * @Description 修改脚本内容实体
 **/
@Data
@ApiModel(value = "ReqUpdateScriptVo", description = "修改脚本内容实体")
public class ReqUpdateScriptVo {

    @ApiModelProperty(value = "脚本key", example = "asdads",required = true)
    @NotNull(message = "脚本key不能为空")
    private String scriptKey;

    @ApiModelProperty(value = "脚本内容", example = "",required = true)
    @NotNull(message = "脚本内容不能为空")
    private String script;


}
