package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/3 16:31
 * @desc 新增角色参数类
 **/
@Data
@ApiModel(value = "addRoleReqVo", description = "新增角色参数类")
public class AddRoleReqVo {
    @ApiModelProperty(value = "角色名", example = "admin", required = true)
    @NotNull(message = "角色名不能为空")
    private String roleName;

    @ApiModelProperty(value = "描述", example = "admin", required = true)
    @NotNull(message = "描述")
    private String description;
}
