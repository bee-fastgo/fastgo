package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/22 11:08
 * @ClassName ResScriptVo
 * @Description 返回的脚本实体
 **/
@Data
@ApiModel(value = "ResScriptVo", description = "返回的脚本实体")
public class ResScriptVo {

    @ApiModelProperty(value = "脚本key", example = "asdads")
    private String scriptKey;

    @ApiModelProperty(value = "脚本名", example = "xxx安装脚本")
    private String scriptName;

    @ApiModelProperty(value = "软件名(来源于字典)", example = "redis")
    private String softwareName;

    @ApiModelProperty(value = "版本号", example = "5.0.8")
    private String version;

    @ApiModelProperty(value = "脚本类型(1.安装 2.修改 3.重启 4.停止 5.卸载)", example = "1")
    private String type;

}
