package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_profile_config_relation
 *
 * @author hs
 * @date   2020/07/28
 */
@Data
public class ProfileConfigRelationDo extends LavaDo {
    /**
     * 项目环境code
     */
    private String profileCode;

    /**
     * 配置中心code
     */
    private String configCode;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.ProfileConfigRelationBo";
    }
}