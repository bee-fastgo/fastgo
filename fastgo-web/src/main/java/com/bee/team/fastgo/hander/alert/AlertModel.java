package com.bee.team.fastgo.hander.alert;

import java.util.Map;

/**
 * @author jgz
 * @date 2020/8/5
 * @desc 告警实体
 **/
public class AlertModel {

    /**
     * 规则
     */
    private String rule;

    /**
     * 钉钉token
     */
    private String token;

    /**
     * 钉钉url
     */
    private String url;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 监控类型
     */
    private String type;

    /**
     * 消息体
     */
    private Map<String,String> info;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

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
