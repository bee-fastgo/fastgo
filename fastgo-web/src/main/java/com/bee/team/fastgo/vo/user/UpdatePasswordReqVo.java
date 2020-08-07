package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xqx
 * @date 2020/8/7 14:48
 * @desc 修改密码的参数
 **/
@ApiModel(value = "updatePasswordReqVo")
@Data
public class UpdatePasswordReqVo {
    @ApiModelProperty(value = "用户名", example = "admin", required = true)
    @NotNull(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "新密码", example = "123456", required = true)
    @NotNull(message = "新密码不能为空")
    private String newPass;

    @ApiModelProperty(value = "确认密码", example = "123456", required = true)
    @NotNull(message = "确认密码不能为空")
    private String confirmPass;
}
