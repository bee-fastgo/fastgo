package com.bee.team.fastgo.vo.project;

import lombok.Data;

/**
 * @author hs
 * @date 2020/7/30 9:35
 * @desc 项目部署条件vo
 **/

@Data
public class DeployInfoVo {

    /**
     * 项目名
     */
    private String projectName;
    /**
     * git地址
     */
    private String gitUrl;
    /**
     * 分支名
     */
    private String branchName;
    /**
     * 服务器IP
     */
    private String serviceIp;
    /**
     * 运行环境元配置
     */
    private String runProfileConfig;


}
