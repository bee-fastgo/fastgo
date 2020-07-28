package com.bee.team.fastgo.service.api.server;

import com.bee.team.fastgo.service.api.server.dto.res.ResSourceListDTO;

import java.util.List;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 软件源接口
 **/
public interface SourceApi {

    /**
     * 获取软件资源源列表
      * @param
     * @return {@link List<ResSourceListDTO>}
     * @author jgz
     * @date 2020/7/28
     * @desc
     */
    List<ResSourceListDTO> getSourceList();

}
