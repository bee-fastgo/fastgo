package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.handler.IJobHandler;
import com.bee.team.fastgo.mapper.ServerExecutorLogDoMapperExt;
import com.bee.team.fastgo.model.ServerExecutorLogDo;
import com.bee.team.fastgo.model.ServerExecutorLogDoExample;
import com.bee.team.fastgo.service.server.ServerExecutorLogBo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.support.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServerExecutorLogBoImpl extends AbstractLavaBoImpl<ServerExecutorLogDo, ServerExecutorLogDoMapperExt, ServerExecutorLogDoExample> implements ServerExecutorLogBo {

    @Autowired
    public void setBaseMapper(ServerExecutorLogDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void addServerExecutorLogDo(ServerExecutorLogDo serverExecutorLogDo) {
        insert(serverExecutorLogDo);
    }

    @Override
    public void modifyServerExecutorLogDo(ServerExecutorLogDo serverExecutorLogDo) {
        update(serverExecutorLogDo);
    }

    @Override
    public ServerExecutorLogDo getServerExecutorLogDoById(String logId) {
        return selectByPrimaryKey(Long.valueOf(logId));
    }

    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        for (HandleCallbackParam handleCallbackParam : callbackParamList) {
            ReturnT<String> callbackResult = callback(handleCallbackParam);
            logger.debug(">>>>>>>>> JobApiController.callback {}, handleCallbackParam={}, callbackResult={}",
                    (callbackResult.getCode() == IJobHandler.SUCCESS.getCode() ? "success" : "fail"), handleCallbackParam, callbackResult);
        }

        return ReturnT.SUCCESS;
    }

    private ReturnT<String> callback(HandleCallbackParam handleCallbackParam) {
        // valid log item
        ServerExecutorLogDo serverExecutorLogDo = this.getServerExecutorLogDoById(String.valueOf(handleCallbackParam.getLogId()));

        if (serverExecutorLogDo == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "log item not found.");
        }
//        if (serverExecutorLogDo.getHandleCode() > 0) {
//                // avoid repeat callback, trigger child job etc
//            return new ReturnT<String>(ReturnT.FAIL_CODE, "log repeate callback.");
//        }


        // handle msg
        StringBuffer handleMsg = new StringBuffer();
        if (handleCallbackParam.getExecuteResult().getMsg() != null) {
            handleMsg.append("<br>"+handleCallbackParam.getExecuteResult().getMsg()+"</br>");
        }

        if (handleMsg.length() > 15000) {
            handleMsg = new StringBuffer(handleMsg.substring(0, 15000));  // text最大64kb 避免长度过长
        }

        // success, save log
        serverExecutorLogDo.setHandleTime(new Date());
        serverExecutorLogDo.setHandleCode(handleCallbackParam.getExecuteResult().getCode());

        serverExecutorLogDo.setHandleWebMsg(handleMsg.toString());
        serverExecutorLogDo.setHandleMsg(handleCallbackParam.getExecuteResult().getMsg());
        serverExecutorLogDo.setStatus(CommonConstant.CODE1);
        this.modifyServerExecutorLogDo(serverExecutorLogDo);
        return ReturnT.SUCCESS;
    }
}