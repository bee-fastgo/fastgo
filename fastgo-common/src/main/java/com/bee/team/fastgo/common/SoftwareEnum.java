package com.bee.team.fastgo.common;

/**
 * @desc: 软件环境枚举
 * @auth: hjs
 * @date: 2020/7/20 0020
 */
public enum SoftwareEnum {

    MYSQL("mysql"),
    REDIS("redis"),
    KAFKA("kafka"),
    MONGODB("mongodb"),
    CASSANDRA("cassandra"),
    SHIRO("shiro"),
    ELASTICSEARCH("elasticsearch"),
    FLUME("flume"),
    ZOOKEEPER("zookeeper"),
    DOCKER("docker");


    private String softwareName;

    SoftwareEnum(String softwareName) {
        this.softwareName = softwareName;
    }
}
