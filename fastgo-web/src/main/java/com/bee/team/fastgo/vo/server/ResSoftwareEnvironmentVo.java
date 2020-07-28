package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 返回的软件环境实体
 **/
@Data
@ApiModel(value = "ResSoftwareEnvironmentVo", description = "返回的软件环境实体")
public class ResSoftwareEnvironmentVo {

    @ApiModelProperty(value = "服务器ip", example = "192.168.1.100")
    private String serverIp;

    @ApiModelProperty(value = "软件环境的唯一code", example = "asdfadfaf")
    private String softwareCode;

    @ApiModelProperty(value = "软件名(来源于字典)", example = "mysql")
    private String softwareName;

    @ApiModelProperty(value = "版本号", example = "5.7")
    private String version;

    @ApiModelProperty(value = "基本配置(json串)", example = "")
    private String softwareConfig;

}
