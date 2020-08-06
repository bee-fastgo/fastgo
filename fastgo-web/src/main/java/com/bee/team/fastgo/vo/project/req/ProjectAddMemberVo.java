package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/8/6 17:44
 * @desc 项目添加成员vo
 **/

@Data
public class ProjectAddMemberVo {

    //用户id
    private Integer userId;

    //项目id
    private Integer projectId;
}
