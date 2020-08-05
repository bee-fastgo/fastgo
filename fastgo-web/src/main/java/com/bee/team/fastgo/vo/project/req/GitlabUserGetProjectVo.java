package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;

/**
 * @author hs
 * @date 2020/8/4 10:13
 * @desc 项目分配gitlab用户
 **/

@Data
@ApiModel(value = "gitlabUserGetProjectVo",description = "项目分配gitlab用户vo")
public class GitlabUserGetProjectVo {

    @ApiModelProperty(value = "项目id",example = "1")
    private Integer projectId;

    @ApiModelProperty(value = "用户ids",example = "[1,2,3,4]")
    private List<Integer> userIds;

}
