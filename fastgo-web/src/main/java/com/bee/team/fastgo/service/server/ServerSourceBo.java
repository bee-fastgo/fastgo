package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.model.ServerSourceDoExample;

import java.util.List;

public interface ServerSourceBo extends LavaBo<ServerSourceDo, ServerSourceDoExample> {
    /**
     * 添加资源
     *
     * @param serverSourceDo
     * @return
     * @author xqx
     * @date 2020/7/28
     * @desc 添加资源
     */
    void insertSource(ServerSourceDo serverSourceDo);

    /**
     * 根据唯一标识查询资源
     *
     * @param sourceCode
     * @return {@link ServerSourceDo}
     * @author xqx
     * @date 2020/7/28
     * @desc 根据唯一标识查询资源
     */
    ServerSourceDo getSourceBySourceCode(String sourceCode);

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
     * @return {@link List< ServerSourceDo>}
     * @author xqx
     * @date 2020/7/28
     * @desc 获取资源列表
     */

    List<ServerSourceDo> getSourcesList();
}