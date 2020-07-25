package com.bee.team.fastgo.exception.sever;

import com.spring.simple.development.support.exception.GlobalResponseCode;

/**
 * @desc: 监控相关自定义异常
 * @auth: hjs
 * @date: 2020/7/24 0024
 */
public class MonitorException {

    /**
     * 系统信息不存在
     */
    public static final GlobalResponseCode SERVER_SYSTEM_NOT_EXIST = new GlobalResponseCode(1901, "server systemInfo not exist", "%s");

    /**
     * 数据吞吐量不存在
     */
    public static final GlobalResponseCode SERVER_NET_IO_NOT_EXIST = new GlobalResponseCode(1902, "server net io not exist", "%s");

    /**
     * 负载均衡信息不存在
     */
    public static final GlobalResponseCode SERVER_LOAD_NOT_EXIST = new GlobalResponseCode(1903, "server load not exist", "%s");

    /**
     * cpu状态信息不存在
     */
    public static final GlobalResponseCode SERVER_CPU_NOT_EXIST = new GlobalResponseCode(1904, "server cpu not exist", "%s");

    /**
     * 内存信息不存在
     */
    public static final GlobalResponseCode SERVER_MEMORY_NOT_EXIST = new GlobalResponseCode(1905, "server memory not exist", "%s");
}
