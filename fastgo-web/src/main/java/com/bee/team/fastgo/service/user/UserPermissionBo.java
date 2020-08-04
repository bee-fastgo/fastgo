package com.bee.team.fastgo.service.user;//package com.bee.team.fastgo.service.user;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.UserPermissionDo;
import com.bee.team.fastgo.model.UserPermissionDoExample;
import com.bee.team.fastgo.vo.user.AddPermissionReqVo;
import com.bee.team.fastgo.vo.user.ListPermissionResVo;
import com.bee.team.fastgo.vo.user.UpdatePermissionReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.List;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 权限管理
 **/
public interface UserPermissionBo extends LavaBo<UserPermissionDo, UserPermissionDoExample> {
    /**
     * 获取权限列表信息
     *
     * @param pageNum
     * @param pageSize
     * @return {@link ResPageDTO}
     * @author xqx
     * @date 2020/8/3
     * @desc 获取权限列表信息
     */
    ResPageDTO listPermissions(Integer pageNum, Integer pageSize);

    /**
     * 根据权限id列表获取权限列表信息
     *
     * @param permissionIds
     * @return {@link List< ListPermissionResVo>}
     * @author xqx
     * @date 2020/8/4
     * @desc 根据权限id列表获取权限列表信息
     */
    List<ListPermissionResVo> getUserBindPermissionList(List<Long> permissionIds);

    /**
     * 添加权限信息
     *
     * @param addPermissionReqVo
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 添加权限信息
     */
    void insertPermission(AddPermissionReqVo addPermissionReqVo);

    /**
     * 修改权限信息
     *
     * @param updatePermissionReqVo
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 修改权限信息
     */
    void updatePermission(UpdatePermissionReqVo updatePermissionReqVo);

    /**
     * 删除权限
     *
     * @param id
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 删除权限
     */
    void deletePermission(Long id);
}