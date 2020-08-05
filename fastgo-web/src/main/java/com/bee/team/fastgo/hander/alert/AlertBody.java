package com.bee.team.fastgo.hander.alert;

import java.util.Map;

/**
 * @author jgz
 * @date 2020/8/5
 * @desc 告警体
 **/
public class AlertBody {

    /**
     * 监控类型
     */
    private String type;

    /**
     * 消息体
     */
    private Map<String,String> info;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }
}
