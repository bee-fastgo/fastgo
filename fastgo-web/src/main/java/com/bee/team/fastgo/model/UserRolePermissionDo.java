package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_user_role_permission
 *
 * @author xqx
 * @date   2020/08/03
 */
@Data
public class UserRolePermissionDo extends LavaDo {
    /**
     * 乐观锁版本号
     */
    private Long optimistic;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.UserRolePermissionBo";
    }
}