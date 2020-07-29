package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.model.ServerSourceDoExample;
import com.bee.team.fastgo.vo.server.PageResourceReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

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

    /**
     * 删除资源
     *
     * @param sourceCode
     * @return
     * @author xqx
     * @date 2020/7/28
     * @desc 删除资源
     */
    void deleteSource(String sourceCode);

    /**
     * 分页获取软件列表信息
     *
     * @param pageResourceReqVo
     * @return {@link ResPageDTO}
     * @author xqx
     * @date 2020/7/29
     * @desc 分页获取软件列表信息
     */

    ResPageDTO listResources(PageResourceReqVo pageResourceReqVo);
}