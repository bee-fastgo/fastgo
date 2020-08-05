package com.bee.team.fastgo.context;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author hs
 * @date 2020/8/4 11:53
 * @desc 项目分配用户权限事件
 **/
public class ProjectAccessEvent extends ApplicationEvent {

    //用户ids
    private List<Integer> userIds;

    //项目id
    private Integer projectId;

    //操作类型 1-添加，2-移除
    private Integer type;

    public ProjectAccessEvent(Object source,Integer projectId,List<Integer> userIds,Integer type) {
        super(source);
        this.projectId = projectId;
        this.userIds = userIds;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
