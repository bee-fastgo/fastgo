package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/3 17:13
 * @desc 修改权限的参数
 **/
@Data
@ApiModel(value = "updatePermissionReqVo", description = "修改权限的参数")
public class UpdatePermissionReqVo {
    @ApiModelProperty(value = "id", example = "1", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "权限名称", example = "添加用户", required = true)
    @NotNull(message = "权限名不能为空")
    private String permissionName;

    @ApiModelProperty(value = "权限key", example = "add_user", required = true)
    @NotNull(message = "权限key不能为空")
    private String permissionKey;

    @ApiModelProperty(value = "描述", example = "添加权限", required = true)
    @NotNull(message = "描述不能为空")
    private String description;
}
