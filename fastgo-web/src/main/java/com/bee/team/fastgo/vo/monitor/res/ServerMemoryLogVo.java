package com.bee.team.fastgo.vo.monitor.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 服务器内存信息vo
 * @auth hjs
 * @date 2020-07-25
 **/
@Data
@ApiModel(value = "ServerMemoryLogVo", description = "服务器内存信息vo")
public class ServerMemoryLogVo implements Serializable {

    @ApiModelProperty(value = "已使用百分比", example = "15%")
    private String memUsePer;

    @ApiModelProperty(value = "生成时间", example = "Date")
    private Date gmtCreate;
}
