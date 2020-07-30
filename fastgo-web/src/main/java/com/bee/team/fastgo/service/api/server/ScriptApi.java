package com.bee.team.fastgo.service.api.server;

import com.bee.team.fastgo.service.api.server.dto.req.*;

/**
 * 脚本相关api
 * @author jgz
 * @date 13:41 2020/7/25
 */
public interface ScriptApi {

    /**
     * 为对应的服务器执行安装脚本
     * @param reqExecInstallScriptDTO 请求体
     * @return {@link java.lang.String 查询id}
     * @author jgz
     * @date 13:23 2020/7/25
     * @desc
     */
    String execInstallScript(ReqExecInstallScriptDTO reqExecInstallScriptDTO);


    /**
     * 为对应的服务器执行卸载脚本
      * @param reqExecUnInstallScriptDTO
     * @return {@link java.lang.String 查询id}
     * @author jgz
     * @date 2020/7/28
     * @desc
     */
    String execUninstallScript(ReqExecUnInstallScriptDTO reqExecUnInstallScriptDTO);

    /**
     * 执行停止脚本
      * @param reqExecStopScriptDTO
     * @return {@link java.lang.String 查询id}
     * @author jgz
     * @date 2020/7/30
     * @desc
     */
    String execStopScript(ReqExecStopScriptDTO reqExecStopScriptDTO);

    /**
     * 执行重启脚本
      * @param reqExecRestartScriptDTO
     * @return {@link java.lang.String 查询id}
     * @author jgz
     * @date 2020/7/30
     * @desc
     */
    String execRestartScript(ReqExecRestartScriptDTO reqExecRestartScriptDTO);


    /**
     * 执行跟新配置脚本
      * @param reqExecUpdateScriptDTO
     * @return {@link java.lang.String 查询id}
     * @author jgz
     * @date 2020/7/30
     * @desc
     */
    String execUpdateScript(ReqExecUpdateScriptDTO reqExecUpdateScriptDTO);
}
