package com.bee.team.fastgo.service.api.server;

import com.bee.team.fastgo.service.api.dto.server.req.ReqExecScriptDTO;

/**
 * 脚本相关api
 * @author jgz
 * @date 13:41 2020/7/25
 */
public interface ScriptApi {

    /**
     * 为对应的服务器执行脚本
     * @param reqExecScriptDTO 请求体
     * @return {@link java.lang.String 查询id}
     * @author jgz
     * @date 13:23 2020/7/25
     * @desc
     */
    String execScript(ReqExecScriptDTO reqExecScriptDTO);

}
