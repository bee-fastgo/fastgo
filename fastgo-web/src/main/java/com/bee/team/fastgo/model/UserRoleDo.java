package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @author xqx
 * @description MyBatis Generator 自动创建,对应数据表为：t_user_role
 * @date 2020/08/04
 */
@Data
public class UserRoleDo extends LavaDo {
    /**
     * 乐观锁版本号
     */
    private Long optimistic;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 描述
     */
    private String description;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.user.UserRoleBo";
    }
}