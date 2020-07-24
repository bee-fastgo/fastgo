package com.bee.team.fastgo.context;

import org.springframework.context.ApplicationEvent;

public class ProjectEvent extends ApplicationEvent {

    private String projectCode;

    private String url;

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

    public ProjectEvent(Object source,String projectCode,String url) {
        super(source);
        this.projectCode = projectCode;
        this.url = url;
    }
}
