package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/4 11:12
 * @desc 修改用户角色的参数信息
 **/
@Data
@ApiModel(value = "updateUserRoleReqVo", description = "修改用户角色的参数信息")
public class UpdateUserRoleReqVo {
    @ApiModelProperty(value = "用户id", example = "1", required = true)
    @NotNull(message = "用户id不能为空")
    private Long id;

    @ApiModelProperty(value = "角色id", example = "1", required = true)
    @NotNull(message = "角色不能为空")
    private Long roleId;

}
