package com.bee.team.fastgo.service.monitor;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerLoadLogDo;
import com.bee.team.fastgo.model.ServerLoadLogDoExample;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerLoadLogVo;

import java.util.List;

public interface ServerLoadLogBo extends LavaBo<ServerLoadLogDo, ServerLoadLogDoExample> {

    /**
     * 添加服务器负载均衡信息
     *
     * @param serverLoadLogDo
     */
    void saveLoadLog(ServerLoadLogDo serverLoadLogDo);

    /**
     * 查询服务器负载均衡信息
     *
     * @param reqVo
     * @return
     */
    List<ServerLoadLogVo> getServerLoadLogList(ServerMonitorLogReqVo reqVo);
}