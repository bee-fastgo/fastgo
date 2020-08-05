package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_user_project
 *
 * @author hs
 * @date   2020/08/03
 */
@Data
public class UserProjectDo extends LavaDo {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 项目id
     */
    private Long projectId;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.UserProjectBo";
    }
}