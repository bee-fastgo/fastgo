package com.bee.team.fastgo.service.user;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.model.UserDoExample;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import javax.servlet.http.HttpServletRequest;

public interface UserBo extends LavaBo<UserDo, UserDoExample> {
    /**
     * @return
     * @Author xqx
     * @Description 登录管理
     * @Date 15:49 2020/7/23
     * @Param userName 用户名
     * @Param password 密码
     **/
    void login(HttpServletRequest request, String userName, String password);

    /**
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
}