package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/8/4 10:06
 * @desc gitlab用户新增信息
 **/
@Data
@ApiModel(value = "gitlabUserInfoVo",description = "gitlab用户新增信息vo")
public class GitlabUserInfoVo {

    @ApiModelProperty(value = "真实姓名",example = "tulin")
    private String name;

    @ApiModelProperty(value = "邮箱",example = "tulin@163.com")
    private String email;

    @ApiModelProperty(value = "用户名",example = "tulin")
    private String username;

    @ApiModelProperty(value = "密码",example = "tulin123")
    private String password;

}
