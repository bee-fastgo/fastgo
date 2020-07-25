package com.bee.team.fastgo.service.api.server;
/**
 * 脚本相关api
 * @author jgz
 * @date 13:41 2020/7/25
 */
public interface ScriptApi {

    /**
     * 为对应的服务器执行脚本
     * @param softwareName 软件名
     * @param version 版本
     * @param type 类型
     * @param ip 服务器ip
     * @return {@link java.lang.String 查询id}
     * @author jgz
     * @date 13:23 2020/7/25
     * @desc
     */
    String execScript(String softwareName,String version,String type,String ip);

}
