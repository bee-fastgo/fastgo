package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.job.core.biz.ExecutorBiz;
import com.bee.team.fastgo.job.core.biz.model.LogParam;
import com.bee.team.fastgo.job.core.biz.model.LogResult;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.model.ServerExecutorLogDo;
import com.bee.team.fastgo.server.core.scheduler.SimpleJobScheduler;
import com.bee.team.fastgo.service.server.ServerExecutorLogBo;
import com.bee.team.fastgo.vo.server.ServerExecutorLogVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: luke
 * @time: 2020/7/21 0021 8:51
 */
@Api(tags = "执行日志相关")
@RestController
@RequestMapping("/logs")
public class ServerExecutorLogController {
    @Autowired
    private BaseSupport baseSupport;
    @Autowired
    private ServerExecutorLogBo serverExecutorLogBo;

    @ApiOperation(value = "执行中查看日志")
    @RequestMapping(value = "/logDetailCat", method = RequestMethod.GET)
    public ResBody<LogResult> logDetailCat(@ApiParam(name = "logId", value = "任务ID") Long logId, @ApiParam(name = "fromLineNum", value = "加载行数") Integer fromLineNum) throws Exception {
        if (logId == null || fromLineNum == null) {
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "参数为空");
        }
        ServerExecutorLogDo serverExecutorLogDo = serverExecutorLogBo.getServerExecutorLogDoById(logId.toString());
        if (serverExecutorLogDo == null) {
            throw new GlobalException(ResponseCode.RES_DATA_NOT_EXIST, "任务不存在");
        }
        ExecutorBiz executorBiz = SimpleJobScheduler.getExecutorBiz(serverExecutorLogDo.getExecutorAddress());
        ReturnT<LogResult> logResult = executorBiz.log(new LogParam(serverExecutorLogDo.getTriggerTime().getTime(), logId, fromLineNum));

        // is end
        if (logResult.getContent() != null && logResult.getContent().getFromLineNum() > logResult.getContent().getToLineNum()) {
            if (serverExecutorLogDo.getHandleCode() > 0) {
                logResult.getContent().setEnd(true);
            }
            return new ResBody().buildSuccessResBody(logResult.getContent());
        }
        return new ResBody().buildSuccessResBody();
    }

    @ApiOperation(value = "查看日志")
    @RequestMapping(value = "/logDetail", method = RequestMethod.GET)
    public ResBody<ServerExecutorLogVo> logDetail(@ApiParam(name = "logId", value = "任务ID") String logId) {
        if (logId == null) {
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "参数为空");
        }
        ServerExecutorLogDo serverExecutorLogDo = serverExecutorLogBo.getServerExecutorLogDoById(logId);
        if (serverExecutorLogDo == null) {
            throw new GlobalException(ResponseCode.RES_DATA_NOT_EXIST, "任务不存在");
        }
        ServerExecutorLogVo serverExecutorLogVo = baseSupport.objectCopy(serverExecutorLogDo, ServerExecutorLogVo.class);
        return new ResBody().buildSuccessResBody(serverExecutorLogVo);
    }
}
