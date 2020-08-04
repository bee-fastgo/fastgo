package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/3 18:34
 * @desc 修改菜单的参数
 **/
@Data
@ApiModel(value = "updateMenuReqVo", description = "修改菜单的参数")
public class UpdateMenuReqVo {
    @ApiModelProperty(value = "id", example = "1", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "菜单名", example = "用户管理", required = true)
    @NotNull(message = "菜单名不能为空")
    private String menuName;

    @ApiModelProperty(value = "菜单URL", example = "/user")
    private String menuUrl;

    @ApiModelProperty(value = "排序", example = "100", required = true)
    @NotNull(message = "排序不能为空")
    private Integer orderNumber;

    @ApiModelProperty(value = "图标", example = "img")
    private String icon;

    @ApiModelProperty(value = "权限id", example = "1")
    private Long permissionId;

}
