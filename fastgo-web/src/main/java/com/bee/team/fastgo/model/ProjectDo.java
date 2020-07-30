package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_project
 *
 * @author hs
 * @date   2020/07/28
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
     * 项目状态：1.项目创建中，2.项目创建成功，3.项目部署中 4.项目部署完成，5-软件环境部署成功，6-运行环境部署成功，7-项目部署失败
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

    /**
     * 自动部署开关： 0-关 1-开
     */
    private Integer autoDeploy;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.ProjectBo";
    }
}