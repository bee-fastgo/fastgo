package com.bee.team.fastgo.context;

import org.springframework.context.ApplicationEvent;

public class ProjectEvent extends ApplicationEvent {

    private String projectCode;

    private String url;

    //0-新增webhook，1-删除webhook
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProjectEvent(Object source,String projectCode,String url,Integer type) {
        super(source);
        this.projectCode = projectCode;
        this.url = url;
        this.type = type;
    }
}
