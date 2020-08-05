package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/3 17:03
 * @desc 添加权限的额参数类
 **/
@Data
@ApiModel(value = "addPermissionReqVo", description = "添加权限的参数类")
public class AddPermissionReqVo {
    @ApiModelProperty(value = "权限名称", example = "添加用户", required = true)
    @NotNull(message = "权限名不能为空")
    private String permissionName;

    @ApiModelProperty(value = "权限key", example = "add_user", required = true)
    @NotNull(message = "权限key不能为空")
    private String permissionKey;

    @ApiModelProperty(value = "父权限id(为空表示为根权限)", example = "1")
    private String parentPermissionId;

    @ApiModelProperty(value = "描述", example = "添加权限", required = true)
    @NotNull(message = "描述不能为空")
    private String description;
}
