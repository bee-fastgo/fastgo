package com.bee.team.fastgo.vo.monitor.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 查询数据吞吐量信息Vo
 * @auth hjs
 * @date 2020-07-25
 **/
@Data
@ApiModel(value = "ServerMonitorVo", description = "查询数据吞吐量信息Vo")
public class ServerNetIOLogVo implements Serializable {

    @ApiModelProperty(value = "每秒钟接收的数据包,rxpck/s", example = "25")
    private String rxpck;

    @ApiModelProperty(value = "每秒钟发送的数据包,txpck/s", example = "25")
    private Double txpck;

    @ApiModelProperty(value = "每秒钟接收的KB数,rxkB/s", example = "25")
    private Double rxbyt;

    @ApiModelProperty(value = "每秒钟发送的KB数,txkB/s", example = "25")
    private String txbyt;

    @ApiModelProperty(value = "生成时间", example = "Date")
    private Date gmtCreate;
}
