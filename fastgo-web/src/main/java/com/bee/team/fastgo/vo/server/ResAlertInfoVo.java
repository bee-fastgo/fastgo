package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author jgz
 * @date 2020/8/4
 * @desc 返回的告警消息体
 **/
@Data
@ApiModel(value = "ResAlertInfoVo", description = "返回的告警消息体")
public class ResAlertInfoVo {

    @ApiModelProperty(value = "告警时间",  example = "xxx")
    private Date alertTime;

    @ApiModelProperty(value = "告警类型: 软件SOFTWARE 项目PROJECT 服务器SERVER", example = "SOFTWARE")
    private String type;

    @ApiModelProperty(value = "告警内容",example = "")
    private String info;


}
