package com.bee.team.fastgo.service.monitor;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerCpuLogDo;
import com.bee.team.fastgo.model.ServerCpuLogDoExample;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerCpuLogVo;

import java.util.List;

public interface ServerCpuLogBo extends LavaBo<ServerCpuLogDo, ServerCpuLogDoExample> {

    /**
     * 添加服务器CPU信息
     *
     * @param serverCpuLogDo
     */
    void saveCpuLog(ServerCpuLogDo serverCpuLogDo);

    /**
     * 查询服务器CPU信息
     *
     * @param reqVo
     * @return
     */
    List<ServerCpuLogVo> getServerCpuLogList(ServerMonitorLogReqVo reqVo);
}