package com.bee.team.fastgo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName UserLoginReqVo
 * @Description 登录参数
 * @Author xqx
 * @Date 2020/7/23 16:09
 * @Version 1.0
 **/
@Data
@ApiModel(value = "userLoginReqVo", description = "登录参数")
public class UserLoginReqVo {
    @ApiModelProperty(value = "用户名", example = "admin", required = true)
    @NotNull(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "密码", example = "123456", required = true)
    @NotNull(message = "密码不能为空")
    private String password;
}
