package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/3 16:38
 * @desc 修改角色的参数
 **/
@Data
@ApiModel(value = "updateRoleReqVo", description = "修改角色的参数")
public class UpdateRoleReqVo {
    @ApiModelProperty(value = "id",example = "1",required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "角色名", example = "admin", required = true)
    @NotNull(message = "角色名不能为空")
    private String roleName;

    @ApiModelProperty(value = "描述", example = "admin", required = true)
    @NotNull(message = "描述")
    private String description;
}
