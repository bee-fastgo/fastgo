package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xqx
 * @date 2020/8/4 15:43
 * @desc 登录用户的信息
 **/
@ApiModel(value = "userInfoResVo", description = "登录用户的信息")
@Data
public class UserInfoResVo {
    @ApiModelProperty(value = "用户名", example = "admin")
    private String userName;

    @ApiModelProperty(value = "权限列表")
    private List<ListPermissionResVo> permissionResVos;

    @ApiModelProperty(value = "菜单列表")
    private List<MenuListResVo> menuListResVos;
}
