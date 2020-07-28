package com.bee.team.fastgo.service.api.server;

import com.bee.team.fastgo.service.api.server.dto.req.ReqCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.dto.req.ReqSoftwareInstallScriptExecResultDTO;
import com.bee.team.fastgo.service.api.server.dto.res.ResCreateSoftwareDTO;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/25 14:27
 * @desc 软件环境api
 **/
public interface SoftwareProfileApi {


    /**
     * 创建软件环境
      * @param reqCreateSoftwareDTO 请求体
     * @return {@link ResCreateSoftwareDTO}
     * @author jgz
     * @date 2020/7/25
     * @desc
     */
    ResCreateSoftwareDTO createSoftwareEnvironment(ReqCreateSoftwareDTO reqCreateSoftwareDTO);



}
