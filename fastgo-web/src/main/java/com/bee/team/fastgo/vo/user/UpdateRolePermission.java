package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xqx
 * @date 2020/8/4 11:29
 * @desc 角色绑定权限的参数类
 **/
@ApiModel(value = "updateRolePermission", description = "角色绑定权限的参数类")
@Data
public class UpdateRolePermission {
    @ApiModelProperty(value = "角色id", example = "1", required = true)
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty(value = "权限id列表", required = true)
    @NotNull(message = "权限列表不能为空")
    private List<Long> permissionIds;
}
