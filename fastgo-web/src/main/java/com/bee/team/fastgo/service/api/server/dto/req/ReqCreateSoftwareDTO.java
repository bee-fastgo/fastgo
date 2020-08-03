package com.bee.team.fastgo.service.api.server.dto.req;

import java.io.Serializable;
import java.util.Map;

/**
 * @author jgz
 * @date 2020/7/25
 * @desc 创建软件的请求实体
 **/
public class ReqCreateSoftwareDTO implements Serializable {

    /**
     * 创建环境的类型
     * 0 创建项目时创建 1 手动创建
     */
    private String createEnvType = "0";

    /**
     * 项目环境code
     */
    private String profileCode;

    /**
     * 服务器ip
     */
    private String ip;

    /**
     * 软件名(来源于字典)
     */
    private String softwareName;

    /**
     * 版本
     */
    private String version;

    /**
     * 软件配置
     */
    private Map<String,String> config;

    public String getCreateEnvType() {
        return createEnvType;
    }

    public void setCreateEnvType(String createEnvType) {
        this.createEnvType = createEnvType;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

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
