package com.bee.team.fastgo.vo.monitor.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @desc 服务器系统信息Vo
 * @auth hjs
 * @date 2020-07-25
 **/
@Data
@ApiModel(value = "ServerSystemInfoVo", description = "服务器系统信息Vo")
public class ServerSystemInfoVo implements Serializable {

    @ApiModelProperty(value = "系统版本信息", example = "8.1.1911")
    private String version;

    @ApiModelProperty(value = "系统详细信息", example = "centOS")
    private String versionDetail;

    @ApiModelProperty(value = "总计内存，G", example = "16")
    private String totalMem;

    @ApiModelProperty(value = "core的个数(即核数)", example = "4")
    private Integer cpuCoreNum;

    @ApiModelProperty(value = "CPU型号信息", example = "Intel(R) Xeon(R) Platinum 8269CY CPU @ 2.50GHz")
    private String cpuModel;

    @ApiModelProperty(value = "主机状态，1正常，2下线", example = "1")
    private Integer state;
}
