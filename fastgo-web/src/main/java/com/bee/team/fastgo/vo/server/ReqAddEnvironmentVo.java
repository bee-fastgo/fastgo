package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author jgz
 * @date 2020/7/27
 * @desc 添加环境配置
 **/
@Data
@ApiModel(value = "ReqAddEnvironmentVo", description = "添加环境配置")
public class ReqAddEnvironmentVo {

    @ApiModelProperty(value = "serverIp", example = "172.22.5.100", required = true)
    @NotNull(message = "服务器ip不能为空")
    private String serverIp;

    @ApiModelProperty(value = "软件名(来源于字典)", example = "mysql", required = true)
    @NotNull(message = "软件名不能为空")
    private String softwareName;

    @ApiModelProperty(value = "版本号", example = "5.7", required = true)
    @NotNull(message = "版本号不能为空")
    private String version;

    @ApiModelProperty(value = "配置,每一项都是key value形式", example = "")
    private Map<String,String> config;

}
