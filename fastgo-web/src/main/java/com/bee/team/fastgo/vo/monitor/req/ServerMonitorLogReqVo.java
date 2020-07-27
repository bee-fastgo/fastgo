package com.bee.team.fastgo.vo.monitor.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @desc 获取监控服务器各项指标状态请求参数类
 * @auth hjs
 * @date 2020-07-25
 **/
@Data
@ApiModel(value = "serverMonitorLogReqVo", description = "获取监控服务器各项状态请求参数类")
public class ServerMonitorLogReqVo implements Serializable {

    @ApiModelProperty(value = "服务器IP", example = "127.0.0.1", required = true)
    @NotEmpty(message = "服务器IP不能为空")
    private String serverIp;

    @ApiModelProperty(value = "查询开始时间", example = "2020-05-02 11:11:11", required = true)
    @NotEmpty(message = "查询开始时间不能为空")
    private String startTime;

    @ApiModelProperty(value = "查询结束时间", example = "2020-05-02 11:11:11", required = true)
    @NotEmpty(message = "查询结束时间不能为空")
    private String endTime;

}
