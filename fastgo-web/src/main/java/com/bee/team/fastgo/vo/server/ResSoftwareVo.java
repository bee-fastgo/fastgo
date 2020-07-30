package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jgz
 * @date 2020/7/30
 * @desc 返回的软件列表
 **/
@Data
@ApiModel(value = "ResSoftwareVo", description = "返回的软件列表")
public class ResSoftwareVo {

    @ApiModelProperty(value = "软件名(来源于字典)", example = "redis")
    public String softwareName;

    @ApiModelProperty(value = "支持的版本列表", example = "5.0.8")
    public List<String> versionList;


}
