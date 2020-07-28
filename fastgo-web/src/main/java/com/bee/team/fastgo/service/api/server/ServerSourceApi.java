package com.bee.team.fastgo.service.api.server;

import com.bee.team.fastgo.model.ServerSourceDo;

import java.util.List;

/**
 * @ClassName ServerSourceApi
 * @Description TODO
 * @Author xqx
 * @Date 2020/7/28 14:43
 * @Version 1.0
 **/
public interface ServerSourceApi {
    /**
     * 根据资源名称和版本号获取资源信息
     *
     * @param sourceName
     * @param sourceVersion
     * @return {@link ServerSourceDo}
     * @author xqx
     * @date 2020/7/28
     * @desc 根据资源名称和版本号获取资源信息
     */
    ServerSourceDo getSourceByNameAndVersion(String sourceName, String sourceVersion);

    /**
     * 获取资源列表
     *
     * @param
     * @return {@link List < ServerSourceDo>}
     * @author xqx
     * @date 2020/7/28
     * @desc 获取资源列表
     */

    List<ServerSourceDo> getSourcesList();
}
