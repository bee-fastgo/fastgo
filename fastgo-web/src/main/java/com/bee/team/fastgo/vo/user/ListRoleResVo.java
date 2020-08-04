package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xqx
 * @date 2020/8/3 16:24
 * @desc 角色列表类
 **/

@Data
@ApiModel(value = "listRoleResVo", description = "角色列表类")
public class ListRoleResVo {
    @ApiModelProperty(value = "id", example = "1")
    private Long id;

    @ApiModelProperty(value = "角色名称", example = "admin")
    private String roleName;

    @ApiModelProperty(value = "描述", example = "admin")
    private String description;
}
