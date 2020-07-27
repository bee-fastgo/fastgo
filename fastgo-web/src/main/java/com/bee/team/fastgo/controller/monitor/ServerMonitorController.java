package com.bee.team.fastgo.controller.monitor;

import com.bee.team.fastgo.service.monitor.*;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerMonitorVo;
import com.bee.team.fastgo.vo.monitor.res.ServerSystemInfoVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc 服务器监控相关API
 * @auth hjs
 * @date 2020-07-25
 **/
@Api(tags = "服务器监控相关API")
@RestController
@RequestMapping("/monitor")
public class ServerMonitorController {

    @Autowired
    private ServerSystemInfoBo serverSystemInfoBo;

    @Autowired
    private ServerCpuLogBo serverCpuLogBo;

    @Autowired
    private ServerLoadLogBo serverLoadLogBo;

    @Autowired
    private ServerNetIOLogBo serverNetIOLogBo;

    @Autowired
    private ServerMemoryLogBo serverMemoryLogBo;


    @ApiOperation(value = "获取服务器系统详细信息")
    @ApiImplicitParam(name = "serverIp", value = "服务器IP", dataTypeClass = String.class)
    @RequestMapping(value = "/getServerSystemInfo", method = RequestMethod.POST, consumes = "application/json")
    public ResBody<ServerSystemInfoVo> getServerSystemInfo(String serverIp) {
        return new ResBody().buildSuccessResBody(serverSystemInfoBo.getServerSystemInfoDo(serverIp));
    }


    @ApiOperation(value = "获取监控服务器各项指标状态")
    @ApiImplicitParam(name = "serverMonitorLogReqVo", value = "获取监控服务器各项指标状态请求参数类", dataTypeClass = ServerMonitorLogReqVo.class)
    @RequestMapping(value = "/getServerMonitorLog", method = RequestMethod.POST, consumes = "application/json")
    @ValidHandler(key = "serverMonitorLogReqVo", value = ServerMonitorLogReqVo.class, isReqBody = false)
    public ResBody<ServerMonitorVo> getServerMonitorLog(ServerMonitorLogReqVo reqVo) {
        ServerMonitorVo serverMonitorVo = new ServerMonitorVo();
        serverMonitorVo.setServerCpuLogVoList(serverCpuLogBo.getServerCpuLogList(reqVo));
        serverMonitorVo.setServerLoadLogVoList(serverLoadLogBo.getServerLoadLogList(reqVo));
        serverMonitorVo.setServerMemoryLogVoList(serverMemoryLogBo.getServerMemoryLogList(reqVo));
        serverMonitorVo.setServerNetIOLogVoList(serverNetIOLogBo.getServerNetIOLogList(reqVo));
        return new ResBody().buildSuccessResBody(serverMonitorVo);
    }


}
