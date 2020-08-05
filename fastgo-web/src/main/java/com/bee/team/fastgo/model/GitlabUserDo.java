package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_gitlab_user
 *
 * @author hs
 * @date   2020/08/04
 */
@Data
public class GitlabUserDo extends LavaDo {
    /**
     * 用户名
     */
    private String gitlabUserName;

    /**
     * 密码
     */
    private String gitlabPassword;

    /**
     * 注册用户姓名
     */
    private String gitlabName;

    /**
     * 注册用户email
     */
    private String gitlabEmail;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 账户状态 1-active，2-blocked
     */
    private Integer status;

    /**
     * gitlab账号id
     */
    private Integer gitlabUserId;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.project.GitlabUserBo";
    }
}