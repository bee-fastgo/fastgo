package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_project
 *
 * @author liko
 * @date   2020/07/21
 */
@Data
public class ProjectDo extends LavaDo {
    /**
     * 项目名
     */
    private String projectName;

    /**
     * 项目唯一标识
     */
    private String projectCode;

    /**
     * 项目描述
     */
    private String projectDesc;

    /**
     * 项目git地址
     */
    private String gitUrl;

    /**
     * git api接口凭证
     */
    private String gitToken;

    /**
     * 项目状态
     */
    private String projectStatus;

    /**
     * 项目包名
     */
    private String packageName;

    /**
     * 项目类型
1:前端项目
2:后端项目
     */
    private String projectType;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.ProjectBo";
    }
}