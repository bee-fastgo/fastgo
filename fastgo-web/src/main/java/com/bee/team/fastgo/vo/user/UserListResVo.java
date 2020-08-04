package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xqx
 * @date 2020/8/3 16:00
 * @desc 用户账号和角色信息
 **/
@Data
@ApiModel(value = "userListResVo", description = "用户账号和角色信息")
public class UserListResVo {
    @ApiModelProperty(value = "id(隐藏)", example = "1")
    private Long id;

    @ApiModelProperty(value = "用户名", example = "zhangsan")
    private String userName;

    @ApiModelProperty(value = "角色id",example = "1")
    private Long roleId;

    @ApiModelProperty(value = "角色名", example = "admin")
    private String roleName;
}
