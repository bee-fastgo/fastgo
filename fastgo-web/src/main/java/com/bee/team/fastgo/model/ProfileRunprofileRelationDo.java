package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_profile_runprofile_relation
 *
 * @author hs
 * @date   2020/07/28
 */
@Data
public class ProfileRunprofileRelationDo extends LavaDo {
    /**
     * 项目环境code
     */
    private String profileCode;

    /**
     * 服务器IP
     */
    private String runServerIp;

    /**
     * 运行环境Code
     */
    private String runProfileCode;

    /**
     * 运行环境配置
     */
    private String runProfileConfig;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.ProfileRunprofileRelationBo";
    }
}