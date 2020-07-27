package com.bee.team.fastgo.service.monitor;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerNetIOLogDo;
import com.bee.team.fastgo.model.ServerNetIOLogDoExample;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerNetIOLogVo;

import java.util.List;

public interface ServerNetIOLogBo extends LavaBo<ServerNetIOLogDo, ServerNetIOLogDoExample> {

    /**
     * 添加数据吞吐量信息
     *
     * @param serverNetIOLogDo
     */
    void saveNetIOLog(ServerNetIOLogDo serverNetIOLogDo);

    /**
     * 查询服务器数据吞吐量信息
     *
     * @param reqVo
     * @return
     */
    List<ServerNetIOLogVo> getServerNetIOLogList(ServerMonitorLogReqVo reqVo);
}