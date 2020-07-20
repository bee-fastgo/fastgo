package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import java.util.Date;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_executor_log
 *
 * @author liko
 * @date   2020/07/20
 */
@Data
public class ServerExecutorLogDo extends LavaDo {
    /**
     * 执行地址
     */
    private String executorAddress;

    /**
     * 执行类型
     */
    private String executorHandler;

    /**
     * 执行参数
     */
    private String executorParam;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    /**
     * 触发时间
     */
    private Date triggerTime;

    /**
     * 触发返回状态
     */
    private Integer triggerCode;

    /**
     * 触发返回结果(web)
     */
    private String triggerWebMsg;

    /**
     * 触发返回结果
     */
    private String triggerMsg;

    /**
     * 执行时间
     */
    private Date handleTime;

    /**
     * 执行返回状态
     */
    private Integer handleCode;

    /**
     * 执行返回的结果(web)
     */
    private String handleWebMsg;

    /**
     * 执行返回结果(原始数据)
     */
    private String handleMsg;

    /**
     * 线程Id
     */
    private Integer threadId;

    /**
     * 是否返回
     */
    private String status;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.ServerExecutorLogBo";
    }
}