package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_strategy
 *
 * @author liko
 * @date   2020/08/04
 */
@Data
public class StrategyDo extends LavaDo {
    /**
     * 策略唯一标识
     */
    private String code;

    /**
     * 监控类型
     */
    private String type;

    /**
     * 规则
     */
    private String rule;

    /**
     * 钉钉关键字
     */
    private String keyword;

    /**
     * 钉钉推送地址
     */
    private String url;

    /**
     * 钉钉推送token
     */
    private String token;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.StrategyBo";
    }
}