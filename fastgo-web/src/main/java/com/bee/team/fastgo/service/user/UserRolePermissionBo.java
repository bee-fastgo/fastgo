package com.bee.team.fastgo.service.user;//package com.bee.team.fastgo.service.user;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.UserRolePermissionDo;
import com.bee.team.fastgo.model.UserRolePermissionDoExample;

import java.util.List;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 角色权限管理
 **/
public interface UserRolePermissionBo extends LavaBo<UserRolePermissionDo, UserRolePermissionDoExample> {

    /**
     * 根据角色id获取该角色绑定的权限信息
     *
     * @param roleId
     * @return {@link List< Long>}
     * @author xqx
     * @date 2020/8/3
     * @desc 根据角色id获取该角色绑定的权限信息
     */
    List<Long> listRolePermissions(Long roleId);

    /**
     * 给角色分配权限
     *
     * @param roleId
     * @param permissionIds
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 给角色分配权限
     */
    void updateRolePermission(Long roleId, List<Long> permissionIds);
}