package com.bee.team.fastgo.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_executor_log
 *
 * @author liko
 * @date   2020/07/20
 */
@Data
@ApiModel(value = "ServerExecutorLogVo", description = "执行结果")
public class ServerExecutorLogVo {
    /**
     * 执行地址
     */
    @ApiModelProperty(value = "executorAddress", required = true, example = "1")
    private String executorAddress;

    /**
     * 执行类型
     */
    @ApiModelProperty(value = "executorHandler", required = true, example = "1")
    private String executorHandler;

    /**
     * 执行参数
     */
    @ApiModelProperty(value = "executorParam", required = true, example = "1")
    private String executorParam;

    /**
     * 失败重试次数
     */
    @ApiModelProperty(value = "executorFailRetryCount", required = true, example = "1")
    private Integer executorFailRetryCount;

    /**
     * 触发时间
     */
    @ApiModelProperty(value = "triggerTime", required = true, example = "1")
    private Date triggerTime;

    /**
     * 触发返回状态
     */
    @ApiModelProperty(value = "triggerCode", required = true, example = "1")
    private Integer triggerCode;

    /**
     * 触发返回结果(web)
     */
    @ApiModelProperty(value = "triggerWebMsg", required = true, example = "1")
    private String triggerWebMsg;

    /**
     * 触发返回结果
     */
    @ApiModelProperty(value = "triggerMsg", required = true, example = "1")
    private String triggerMsg;

    /**
     * 执行时间
     */
    @ApiModelProperty(value = "handleTime", required = true, example = "1")
    private Date handleTime;

    /**
     * 执行返回状态
     */
    @ApiModelProperty(value = "handleCode", required = true, example = "1")
    private Integer handleCode;

    /**
     * 执行返回的结果(web)
     */
    @ApiModelProperty(value = "executorAddress", required = true, example = "1")
    private String handleWebMsg;

    /**
     * 执行返回结果(原始数据)
     */
    @ApiModelProperty(value = "handleMsg", required = true, example = "1")
    private String handleMsg;

    /**
     * 线程Id
     */
    @ApiModelProperty(value = "threadId", required = true, example = "1")
    private Integer threadId;

    /**
     * 是否返回
     * 0:未返回
     * 1:已返回
     */
    @ApiModelProperty(value = "status", required = true, example = "1")
    private String status;
}