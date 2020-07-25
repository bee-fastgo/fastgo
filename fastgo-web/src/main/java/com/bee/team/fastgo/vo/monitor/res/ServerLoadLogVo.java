package com.bee.team.fastgo.vo.monitor.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 服务器负载均衡记录Vo
 * @auth hjs
 * @date 2020-07-25
 **/
@Data
@ApiModel(value = "ServerLoadLogVo", description = "服务器负载均衡记录Vo")
public class ServerLoadLogVo implements Serializable {

    @ApiModelProperty(value = "1分钟之前到现在的负载", example = "15.55")
    private Double oneLoad;

    @ApiModelProperty(value = "5分钟之前到现在的负载", example = "15.55")
    private Double fiveLoad;

    @ApiModelProperty(value = "15分钟之前到现在的负载", example = "15.55")
    private Double fifteenLoad;

    @ApiModelProperty(value = "生成时间", example = "Date")
    private Date gmtCreate;
}
