package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_profile_software_relation
 *
 * @author hs
 * @date   2020/07/28
 */
@Data
public class ProfileSoftwareRelationDo extends LavaDo {
    /**
     * 项目环境Code
     */
    private String prifileCode;

    /**
     * 服务器Ip
     */
    private String runServerIp;

    /**
     * 软件环境Code
     */
    private String softwareCode;

    /**
     * 软件环境元配置
     */
    private String softwareConfig;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.ProfileSoftwareRelationBo";
    }
}