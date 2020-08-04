package com.bee.team.fastgo.service.user.impl;//package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.UserRolePermissionDoMapperExt;
import com.bee.team.fastgo.model.UserRolePermissionDo;
import com.bee.team.fastgo.model.UserRolePermissionDoExample;
import com.bee.team.fastgo.service.user.UserRolePermissionBo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 角色权限管理
 **/
@Service
public class UserRolePermissionBoImpl extends AbstractLavaBoImpl<UserRolePermissionDo, UserRolePermissionDoMapperExt, UserRolePermissionDoExample> implements UserRolePermissionBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(UserRolePermissionDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public List<Long> listRolePermissions(Long roleId) {
        UserRolePermissionDoExample example = new UserRolePermissionDoExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<UserRolePermissionDo> list = selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().map(UserRolePermissionDo::getPermissionId).collect(Collectors.toList());
    }

    @Override
    public void updateRolePermission(Long roleId, List<Long> permissionIds) {
        // 获取该角色已经绑定了的权限信息
        List<Long> oldPermissionList = listRolePermissions(roleId);

        // 删除数据存在但不再分配的绑定关系
        if (!CollectionUtils.isEmpty(oldPermissionList)) {
            oldPermissionList.forEach(e -> {
                // 如果修改的参数中不包含数据库中的权限id，就删除数据库的绑定关系
                if (!permissionIds.contains(e)) {
                    UserRolePermissionDoExample example = new UserRolePermissionDoExample();
                    example.createCriteria().andRoleIdEqualTo(roleId).andPermissionIdEqualTo(e);
                    List<UserRolePermissionDo> list = selectByExample(example);
                    if (!CollectionUtils.isEmpty(list)) {
                        UserRolePermissionDo userRolePermissionDo = list.get(0);
                        userRolePermissionDo.setIsDeleted("y");
                        update(userRolePermissionDo);
                    }
                }
                // 如果包含就移除掉该permissionId,最后过滤的就是要额外新增的绑定关系
                permissionIds.remove(e);
            });
        }

        // 新增绑定关系
        permissionIds.forEach(e -> {
            UserRolePermissionDo userRolePermissionDo = new UserRolePermissionDo();
            userRolePermissionDo.setRoleId(roleId);
            userRolePermissionDo.setPermissionId(e);
            insert(userRolePermissionDo);
        });
    }
}