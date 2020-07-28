package com.bee.team.fastgo.service.api.server.dto.res;

import java.io.Serializable;

/**
 * @author jgz
 * @date 2020/7/25
 * @desc 创建软件返回实体
 **/
public class ResCreateSoftwareDTO implements Serializable {

    /**
     * 配置标识(0.新环境 1.已存在的环境)
     */
    private String configFlag;

    /**
     * 软件元配置(json格式的字符串)
     */
    private String softwareConfig;

    /**
     * 软件环境的code
     */
    private String softwareCode;

    public String getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(String softwareCode) {
        this.softwareCode = softwareCode;
    }

    public String getConfigFlag() {
        return configFlag;
    }

    public void setConfigFlag(String configFlag) {
        this.configFlag = configFlag;
    }

    public String getSoftwareConfig() {
        return softwareConfig;
    }

    public void setSoftwareConfig(String softwareConfig) {
        this.softwareConfig = softwareConfig;
    }
}
