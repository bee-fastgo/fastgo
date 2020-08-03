package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_user
 *
 * @author xqx
 * @date   2020/08/03
 */
@Data
public class UserDo extends LavaDo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 角色id
     */
    private Long roleId;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.UserBo";
    }
}