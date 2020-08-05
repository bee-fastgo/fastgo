package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_dynamic_menu
 *
 * @author xqx
 * @date   2020/08/03
 */
@Data
public class DynamicMenuDo extends LavaDo {
    /**
     * 乐观锁版本号
     */
    private Long optimistic;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 菜单URL
     */
    private String menuUrl;

    /**
     * 排序
     */
    private Integer orderNumber;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 父菜单id
     */
    private Long parentMenuId;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.DynamicMenuBo";
    }
}