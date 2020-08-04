package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerRunProfileDo;
import com.bee.team.fastgo.model.ServerRunProfileDoExample;
import com.bee.team.fastgo.vo.server.AddServerRunProfileVo;
import com.bee.team.fastgo.vo.server.DelServerRunProfileVo;
import com.bee.team.fastgo.vo.server.QueryRunProfileVo;
import com.bee.team.fastgo.vo.server.QueryServerVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.List;

/**
 * @author luke
 * @desc 运行环境
 * @date 2020-07-29
 **/
public interface ServerRunProfileBo extends LavaBo<ServerRunProfileDo, ServerRunProfileDoExample> {
    /**
     * 添加运行环境
     *
     * @param addServerRunProfileVo
     */
    ServerRunProfileDo addServerRunProfileDo(AddServerRunProfileVo addServerRunProfileVo);

    /**
     * 销毁运行环境
     *
     * @param delServerRunProfileVo
     */
    void delServerRunProfileDo(DelServerRunProfileVo delServerRunProfileVo);

    /**
     * 获取运行环境
     *
     * @param ip
     * @param port
     * @return
     */
    ServerRunProfileDo getServerRunProfileDo(String ip, int port);

    /**
     * 查询运行环境
     *
     * @param queryRunProfileVo
     * @return
     */
    ResPageDTO queryPageServerRunProfileDo(QueryRunProfileVo queryRunProfileVo);

    /**
     * 获取所有的运行环境
     * @return
     */
    List<ServerRunProfileDo> getListServerRunProfileDo();
}
