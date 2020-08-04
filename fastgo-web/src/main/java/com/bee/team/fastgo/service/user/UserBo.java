package com.bee.team.fastgo.service.user;//package com.bee.team.fastgo.service.user;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.model.UserDoExample;
import com.bee.team.fastgo.vo.user.UserInfoResVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 用户管理
 **/
public interface UserBo extends LavaBo<UserDo, UserDoExample> {
    /**
     * 登录管理
     *
     * @return
     * @Author xqx
     * @Description 登录管理
     * @Date 15:49 2020/7/23
     * @Param userName 用户名
     * @Param password 密码
     **/
    UserInfoResVo login(HttpServletRequest request, String userName, String password);

    /**
     * 添加用户
     *
     * @return
     * @Author xqx
     * @Description 添加用户
     * @Date 15:49 2020/7/23
     * @Param userName 用户名
     * @Param password 密码
     **/
    void insertUser(String userName, String password);

    /**
     * 获取用户列表, 可模糊查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @return {@link ResPageDTO}
     * @author xqx
     * @date 2020/8/3
     * @desc 获取用户列表, 可模糊查询
     */
    ResPageDTO ListUsers(Integer pageNum, Integer pageSize, String name);

    /**
     * 修改用户角色信息
     *
     * @param id
     * @param roleId
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 修改角色信息
     */
    void updateRole(Long id, Long roleId);
}