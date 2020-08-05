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

    /**
     * 添加权限时自动给角色添加该权限
     *
     * @param roleId
     * @param permissionId
     * @return
     * @author xqx
     * @date 2020/8/5
     * @desc 添加权限时自动给角色添加该权限
     */
    void addRolePermission(Long roleId, Long permissionId);

    /**
     * 根据条件获取所有的角色权限绑定关系
     *
     * @param example
     * @return {@link List< UserRolePermissionDo>}
     * @author xqx
     * @date 2020/8/5
     * @desc 根据条件获取所有的角色权限绑定关系
     */
    List<UserRolePermissionDo> getRolePermissionListByCondition(UserRolePermissionDoExample example);

    /**
     * 批量解绑
     *
     * @param list
     * @return
     * @author xqx
     * @date 2020/8/5
     * @desc 批量解绑
     */
    void delRolePermission(List<UserRolePermissionDo> list);
}