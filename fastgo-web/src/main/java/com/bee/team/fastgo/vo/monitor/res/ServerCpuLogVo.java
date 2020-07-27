package com.bee.team.fastgo.vo.monitor.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 服务器cpu使用率记录Vo
 * @auth hjs
 * @date 2020-07-25
 **/
@Data
@ApiModel(value = "ServerCpuLogVo", description = "服务器cpu使用率记录Vo")
public class ServerCpuLogVo implements Serializable {

    @ApiModelProperty(value = "cpu使用率", example = "16.8")
    private Double cpuUse;

    @ApiModelProperty(value = "生成时间", example = "Date")
    private Date gmtCreate;

}
