package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/22 10:14
 * @ClassName ReqAddScriptVo
 * @Description 添加脚本实体
 **/
@Data
@ApiModel(value = "ReqAddScriptVo", description = "添加脚本实体")
public class ReqAddScriptVo {

    @ApiModelProperty(value = "脚本名", example = "xxx安装脚本", required = true)
    @NotNull(message = "脚本名不能为空")
    private String scriptName;

    @ApiModelProperty(value = "软件名(来源于字典)", example = "redis", required = true)
    @NotNull(message = "软件名不能为空")
    private String softwareName;

    @ApiModelProperty(value = "版本号", example = "5.0.8", required = true)
    @NotNull(message = "版本号不能为空")
    private String version;

    @ApiModelProperty(value = "脚本类型(1.安装 2.修改 3.重启 4.停止 5.卸载)", example = "1", required = true)
    @NotNull(message = "脚本类型不能为空")
    private String type;

    @ApiModelProperty(value = "脚本内容", example = "", required = true)
    @NotNull(message = "脚本内容不能为空")
    private String script;

}
