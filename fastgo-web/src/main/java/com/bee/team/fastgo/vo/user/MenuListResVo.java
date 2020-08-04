package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xqx
 * @date 2020/8/3 18:38
 * @desc 菜单列表信息类
 **/
@Data
@ApiModel(value = "menuListResVo", description = "菜单列表信息类")
public class MenuListResVo {
    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "菜单名", example = "用户管理", required = true)
    private String menuName;

    @ApiModelProperty(value = "菜单URL", example = "/user")
    private String menuUrl;

    @ApiModelProperty(value = "排序", example = "100", required = true)
    private Integer orderNumber;

    @ApiModelProperty(value = "图标", example = "img")
    private String icon;

    @ApiModelProperty(value = "权限id", example = "1")
    private Long permissionId;

    @ApiModelProperty(value = "父菜单", example = "1")
    private Long parentMenuId;

    @ApiModelProperty(value = "子菜单")
    private List<MenuListResVo> children = new ArrayList<>();

    public void addChildren(MenuListResVo menuListResVo) {
        children.add(menuListResVo);
    }
}
