package com.bee.team.fastgo.server.core.model;

import lombok.Data;

import java.util.Date;

/**
 * simple-job log, used to track trigger process
 *
 * @author luke  2015-12-19 23:19:09
 */
@Data
public class SimpleJobLog {
	/**
	 * jobId
	 **/
    private long id;

	/**
	 * 线程Id
	 **/
	private Long threadId;
	/**
	 *  execute info
	 **/
    private String executorAddress;
    private String executorHandler;
    private String executorParam;
    private int executorFailRetryCount;

	/**
	 *  trigger info
	 **/
    private Date triggerTime;
    private int triggerCode;
    private String triggerMsg;
    private String triggerWebMsg;

	/**
	 *  handle info
	 **/
    private Date handleTime;
    private int handleCode;
    private String handleMsg;
}
