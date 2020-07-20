package com.bee.team.fastgo.common;

/**
 * @desc: 软件环境枚举
 * @auth: hjs
 * @date: 2020/7/20 0020
 */
public enum SoftwareEnum {

    MYSQL("redis"),
    REDIS("mysql"),
    KAFKA("kafka"),
    MONGODB("mongodb"),
    CASSANDRA("cassandra"),
    SHIRO("shiro"),
    ELASTICSEARCH("elasticsearch");

    private String softwareName;

    SoftwareEnum(String softwareName) {
        this.softwareName = softwareName;
    }
}
