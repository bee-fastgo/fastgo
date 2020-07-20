package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.model.ServerExecutorLogDo;
import com.bee.team.fastgo.model.ServerExecutorLogDoExample;

import java.util.List;

public interface ServerExecutorLogBo extends LavaBo<ServerExecutorLogDo, ServerExecutorLogDoExample> {

    /**
     * @Author luke
     * @Description 保存执行日志
     * @Date 15:20 2020/7/20 0020
     * @Param [serverExecutorLogDo]
     * @return void
     **/
    void addServerExecutorLogDo(ServerExecutorLogDo serverExecutorLogDo);



    /**
     * @Author luke
     * @Description 更新执行日志
     * @Date 15:20 2020/7/20 0020
     * @Param [serverExecutorLogDo]
     * @return void
     **/
    void modifyServerExecutorLogDo(ServerExecutorLogDo serverExecutorLogDo);


    /**
     * @Author luke
     * @Description 查询日志Id
     * @Date 15:20 2020/7/20 0020
     * @Param [serverExecutorLogDo]
     * @return void
     **/
    ServerExecutorLogDo getServerExecutorLogDoById(String logId);
    /**
     * @Author luke
     * @Description 日志回调
     * @Date 15:37 2020/7/20 0020
     * @Param [callbackParamList]
     * @return com.bee.team.fastgo.job.core.biz.model.ReturnT<java.lang.String>
     **/
    ReturnT<String> callback(List<HandleCallbackParam> callbackParamList);
}