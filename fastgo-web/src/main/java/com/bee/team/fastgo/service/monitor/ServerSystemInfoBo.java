package com.bee.team.fastgo.service.monitor;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerSystemInfoDo;
import com.bee.team.fastgo.model.ServerSystemInfoDoExample;
import com.bee.team.fastgo.vo.monitor.res.ServerSystemInfoVo;

public interface ServerSystemInfoBo extends LavaBo<ServerSystemInfoDo, ServerSystemInfoDoExample> {

    /**
     * 添加服务器系统信息
     *
     * @param serverSystemInfoDo
     */
    void saveSystemInfoBoLog(ServerSystemInfoDo serverSystemInfoDo);

    /**
     * 查询服务器系统信息
     *
     * @param serverIp 服务器IP
     * @return
     */
    ServerSystemInfoVo getServerSystemInfoDo(String serverIp);
}