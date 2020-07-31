package com.bee.team.fastgo.vo.project;

import lombok.Data;

/**
 * @author hs
 * @date 2020/7/30 19:50
 * @desc 项目分支和访问地址vo
 **/

@Data
public class ProjectBranchAndAccessAddrVo {

    //项目分支名称
    private String branchName;

    //项目分支访问地址
    private String accessAddr;

    //项目运行环境配置
    private String runProfileConfig;

}
