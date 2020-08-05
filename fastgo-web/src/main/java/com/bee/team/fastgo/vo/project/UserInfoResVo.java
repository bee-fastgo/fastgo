package com.bee.team.fastgo.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/8/4 10:53
 * @desc 用户信息展示
 **/
@Data
@ApiModel(value = "userInfoResVo",description = "用户信息展示vo")
public class UserInfoResVo {

    @ApiModelProperty(value = "用户主键id",example = "1")
    private Integer id;

    @ApiModelProperty(value = "用户名",example = "tulin")
    private String userName;

}
