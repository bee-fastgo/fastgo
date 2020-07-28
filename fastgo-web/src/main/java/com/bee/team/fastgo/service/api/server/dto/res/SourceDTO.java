package com.bee.team.fastgo.service.api.server.dto.res;

import java.io.Serializable;
import java.util.List;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 软件信息
 **/
public class SourceDTO implements Serializable {

    private String softwareName;

    private String version;

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
