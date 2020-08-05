package com.bee.team.fastgo.service.user;//package com.bee.team.fastgo.service.user;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.UserRoleDo;
import com.bee.team.fastgo.model.UserRoleDoExample;
import com.bee.team.fastgo.vo.user.AddRoleReqVo;
import com.bee.team.fastgo.vo.user.UpdateRoleReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.List;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 角色管理
 **/
public interface UserRoleBo extends LavaBo<UserRoleDo, UserRoleDoExample> {
    /**
     * 获取角色列表信息
     *
     * @param pageNum
     * @param pageSize
     * @return {@link ResPageDTO}
     * @author xqx
     * @date 2020/8/3
     * @desc 获取角色列表信息
     */
    ResPageDTO ListRoles(Integer pageNum, Integer pageSize);

    /**
     * 根据id获取角色信息
     *
     * @param id
     * @return {@link UserRoleDo}
     * @author xqx
     * @date 2020/8/3
     * @desc 根据id获取角色信息
     */
    UserRoleDo getRoleById(Long id);

    /**
     * 添加角色信息
     *
     * @param addRoleReqVo
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 添加角色信息
     */
    void insertRole(AddRoleReqVo addRoleReqVo);

    /**
     * 修改角色信息
     *
     * @param updateRoleReqVo
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 修改角色信息
     */
    void updateRole(UpdateRoleReqVo updateRoleReqVo);

    /**
     * 删除角色
     *
     * @param id
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 删除角色
     */
    void deleteRole(Long id);


    /**
     * 根据条件获取角色信息
     *
     * @param example
     * @return {@link UserRoleDo}
     * @author xqx
     * @date 2020/8/5
     * @desc 根据条件获取角色信息
     */
    UserRoleDo getRoleByCondition(UserRoleDoExample example);

    /**
     * 获取所有的角色信息
     *
     * @param
     * @return {@link List< UserRoleDo>}
     * @author xqx
     * @date 2020/8/5
     * @desc 获取所有的角色信息
     */
    List<UserRoleDo> getAllRoles();
}