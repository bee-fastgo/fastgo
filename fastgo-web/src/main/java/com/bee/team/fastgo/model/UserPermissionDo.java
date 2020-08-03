package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_user_permission
 *
 * @author xqx
 * @date   2020/08/03
 */
@Data
public class UserPermissionDo extends LavaDo {
    /**
     * 乐观锁版本号
     */
    private Long optimistic;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限key
     */
    private String permissionKey;

    /**
     * 父权限id
     */
    private String parentPermissionId;

    /**
     * 描述
     */
    private String description;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.UserPermissionBo";
    }
}