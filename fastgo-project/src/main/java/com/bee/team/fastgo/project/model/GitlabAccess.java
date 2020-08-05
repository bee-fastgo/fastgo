package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/8/3 17:21
 * @desc gitlab 访问权限
 **/
@Data
public class GitlabAccess {

    private Integer id;

    //gitlab 用户名
    private String username;

    //gitlab 注册姓名
    private String name;

    //gitlab 账号状态
    private String state;

    //gitlab 访问创建时间
    @JsonProperty("created_at")
    private String createdAt;

    //gitlab 请求时间
    @JsonProperty("requested_at")
    private String requestedAt;

}
