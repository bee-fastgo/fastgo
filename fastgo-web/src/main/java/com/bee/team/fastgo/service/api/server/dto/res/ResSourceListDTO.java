package com.bee.team.fastgo.service.api.server.dto.res;

import java.io.Serializable;
import java.util.List;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 软件源列表
 **/
public class ResSourceListDTO implements Serializable {

    private String ip;

    private List<SourceDTO> sourceDTOList;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<SourceDTO> getSourceDTOList() {
        return sourceDTOList;
    }

    public void setSourceDTOList(List<SourceDTO> sourceDTOList) {
        this.sourceDTOList = sourceDTOList;
    }
}
