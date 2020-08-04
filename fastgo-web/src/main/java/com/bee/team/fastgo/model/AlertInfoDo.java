package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import java.util.Date;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_alert_info
 *
 * @author liko
 * @date   2020/08/04
 */
@Data
public class AlertInfoDo extends LavaDo {
    /**
     * 消息唯一标识
     */
    private String code;

    /**
     * 告警时间
     */
    private Date alertTime;

    /**
     * 监控类型
     */
    private String type;

    /**
     * 消息体
     */
    private String info;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.AlertInfoBo";
    }
}