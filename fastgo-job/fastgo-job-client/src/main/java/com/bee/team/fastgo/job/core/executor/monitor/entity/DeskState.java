package com.bee.team.fastgo.job.core.executor.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc: 查看磁盘大小使用信息
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
@Data
public class DeskState implements Serializable {

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 盘符类型
     */
    private String fileSystem;

    /**
     * 分区大小
     */
    private String size;

    /**
     * 已使用
     */
    private String used;

    /**
     * 可用
     */
    private String avail;

    /**
     * 已使用百分比
     */
    private String usePer;

    /**
     * 创建时间
     */
    private Date createTime;
}
