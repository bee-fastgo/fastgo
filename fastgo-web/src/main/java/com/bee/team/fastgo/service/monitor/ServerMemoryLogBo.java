package com.bee.team.fastgo.service.monitor;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerMemoryLogDo;
import com.bee.team.fastgo.model.ServerMemoryLogDoExample;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerMemoryLogVo;

import java.util.List;

public interface ServerMemoryLogBo extends LavaBo<ServerMemoryLogDo, ServerMemoryLogDoExample> {

    /**
     * 添加服务器内存信息
     *
     * @param serverMemoryLogDo
     */
    void saveMemoryLog(ServerMemoryLogDo serverMemoryLogDo);

    /**
     * 查询服务器内存信息
     *
     * @param reqVo
     * @return
     */
    List<ServerMemoryLogVo> getServerMemoryLogList(ServerMonitorLogReqVo reqVo);
}