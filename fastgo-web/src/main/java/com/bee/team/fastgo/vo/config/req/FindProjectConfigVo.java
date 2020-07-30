package com.bee.team.fastgo.vo.config.req;

import lombok.Data;

/**
 * @author hs
 * @date 2020/7/30 10:13
 * @desc 获取项目配置中心vo
 **/

@Data
public class FindProjectConfigVo {

    //项目code
    private String projectCode;

    //分支名
    private String branchName;
}
