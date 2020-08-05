package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_project_deploy_log
 *
 * @author hs
 * @date   2020/08/03
 */
@Data
public class ProjectDeployLogDo extends LavaDo {
    /**
     * 项目分支名
     */
    private String branchName;

    /**
     * 项目唯一标识
     */
    private String projectCode;

    /**
     * 项目部署用户
     */
    private String user;

    /**
     * 项目部署日志id
     */
    private String deployLogId;

    /**
     * 项目状态：1项目部署中；2-项目部署完成；3-项目部署失败
     */
    private String projectDeployStatus;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.ProjectDeployLogBo";
    }
}