package com.bee.team.fastgo.tools.deploy;

import lombok.Data;

/**
 * @author luke
 * @desc 部署对象
 * @date 2020-07-28
 **/
@Data
public class DeployDTO {
    /**
     * 项目名
     */
    String projectName;
    /**
     * git地址
     */
    String gitUrl;
    /**
     * 分支名
     */
    String branchName;
    /**
     * 服务器IP
     */
    String serviceIp;

    /**
     * 项目端口
     */
    String projectPort;
    /**
     * ssh端口
     */
    Integer servicePort;
    /**
     * sshUser
     */
    String serviceUserName;
    /**
     * ssh 密码
     */
    String serviceUserPassword;

    /**
     * 后端服务地址
     */
    String simpleServiceUrl;

    /**
     * 部署日志Id
     */
    String deployLogId;

}
