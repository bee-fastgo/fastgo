package com.bee.team.fastgo.service.user;

import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.model.UserDoExample;

public interface UserBo extends com.alibaba.lava.base.LavaBo<UserDo, UserDoExample> {
    /**
     * @return
     * @Author xqx
     * @Description 登录管理
     * @Date 15:49 2020/7/23
     * @Param userName 用户名
     * @Param password 密码
     **/
    void login(String userName, String password);

    /**
     * @return
     * @Author xqx
     * @Description 添加用户
     * @Date 15:49 2020/7/23
     * @Param userName 用户名
     * @Param password 密码
     **/
    void insertUser(String userName, String password);
}