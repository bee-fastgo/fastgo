package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xqx
 * @date 2020/8/3 16:52
 * @desc 用户权限类
 **/
@Data
@ApiModel(value = "listPermissionResVo", description = "用户权限类")
public class ListPermissionResVo {
    @ApiModelProperty(value = "id", example = "1")
    private Long id;

    @ApiModelProperty(value = "权限名称", example = "用户添加")
    private String permissionName;

    @ApiModelProperty(value = "权限key", example = "use_add")
    private String permissionKey;

    @ApiModelProperty(value = "父权限id", example = "1")
    private Long parentPermissionId;

    @ApiModelProperty(value = "描述", example = "用户添加")
    private String description;

    @ApiModelProperty(value = "子权限")
    private List<ListPermissionResVo> children = new ArrayList<>();

    public void addChild(ListPermissionResVo listPermissionResVo) {
        children.add(listPermissionResVo);
    }
}
