package com.bee.team.fastgo.service.api.server.dto.req;

import lombok.Data;

/**
 * @author luke
 * @desc simple项目部署
 * @date 2020-07-28
 **/
@Data
public class VueDeployDTO {
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
     * simple服务器地址
     */
    String serviceUrl;
}
