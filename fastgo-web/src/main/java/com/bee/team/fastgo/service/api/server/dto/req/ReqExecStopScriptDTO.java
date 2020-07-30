package com.bee.team.fastgo.service.api.server.dto.req;

import java.io.Serializable;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 停止软件请求体
 **/
public class ReqExecStopScriptDTO implements Serializable {

    /**
     * 目标服务器ip
     */
    private String ip;

    /**
     * 软件名
     */
    private String softwareName;

    /**
     * 版本
     */
    private String version;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
