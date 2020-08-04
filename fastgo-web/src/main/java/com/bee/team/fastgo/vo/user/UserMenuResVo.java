package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xqx
 * @date 2020/8/4 18:29
 * @desc 用户的动态菜单类
 **/
@ApiModel(value = "userMenuResVo", description = "用户的动态菜单类")
@Data
public class UserMenuResVo {
    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "菜单名", example = "用户管理", required = true)
    private String menuName;

    @ApiModelProperty(value = "菜单URL", example = "/user")
    private String menuUrl;

    @ApiModelProperty(value = "图标", example = "img")
    private String icon;

    @ApiModelProperty(value = "路由")
    private String component;

    @ApiModelProperty(value = "父菜单", example = "1")
    private Long parentMenuId;

    @ApiModelProperty(value = "排序", example = "100", required = true)
    private Integer orderNumber;

    @ApiModelProperty(value = "菜单名", example = "user", required = true)
    private String name;

    @ApiModelProperty(value = "标题", example = "user", required = true)
    private String title;

    @ApiModelProperty(value = "子菜单")
    private List<UserMenuResVo> children = new ArrayList<>();

    @ApiModelProperty(value = "path", example = "/user")
    private String path;

    public void addChildren(UserMenuResVo userMenuResVo) {
        children.add(userMenuResVo);
    }
}
