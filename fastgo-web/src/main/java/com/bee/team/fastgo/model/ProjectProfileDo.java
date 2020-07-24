package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import java.util.Date;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_project_profile
 *
 * @author liko
 * @date   2020/07/22
 */
@Data
public class ProjectProfileDo extends LavaDo {
    /**
     * 修改时间
     */
    private Date gmt_codified;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 项目唯一标识
     */
    private String projectCode;

    /**
     * 项目环境名称
     */
    private String profileName;

    /**
     * 分支名
     */
    private String branchName;

    /**
     * 项目环境Code
     */
    private String prifileCode;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.ProjectProfileBo";
    }
}