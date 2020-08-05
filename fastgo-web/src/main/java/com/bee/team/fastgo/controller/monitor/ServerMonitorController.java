package com.bee.team.fastgo.controller.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.service.monitor.*;
import com.bee.team.fastgo.vo.monitor.req.ServerMonitorLogReqVo;
import com.bee.team.fastgo.vo.monitor.res.ServerMonitorVo;
import com.bee.team.fastgo.vo.monitor.res.ServerSystemInfoVo;
import com.spring.simple.development.core.annotation.base.NoLogin;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc 服务器监控相关API
 * @auth hjs
 * @date 2020-07-25
 **/
@Api(tags = "服务器监控相关API")
@RestController
@RequestMapping("/api/monitor")
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

    @Autowired
    private AlertHandler alertHandler;


    @NoLogin
    @ApiOperation(value = "获取服务器系统详细信息")
    @ApiImplicitParam(name = "serverIp", value = "服务器IP", dataTypeClass = String.class)
    @RequestMapping(value = "/getServerSystemInfo", method = RequestMethod.POST, consumes = "application/json")
    public ResBody<ServerSystemInfoVo> getServerSystemInfo(@RequestBody String serverIp) {
        return new ResBody().buildSuccessResBody(serverSystemInfoBo.getServerSystemInfoDo(serverIp));
    }

    @NoLogin
    @ApiOperation(value = "获取监控服务器各项指标状态")
    @ApiImplicitParam(name = "serverMonitorLogReqVo", value = "获取监控服务器各项指标状态请求参数类", dataTypeClass = ServerMonitorLogReqVo.class)
    @RequestMapping(value = "/getServerMonitorLog", method = RequestMethod.POST, consumes = "application/json")
    @ValidHandler(key = "serverMonitorLogReqVo", value = ServerMonitorLogReqVo.class, isReqBody = false)
    public ResBody<ServerMonitorVo> getServerMonitorLog(@RequestBody ServerMonitorLogReqVo reqVo) {
        ServerMonitorVo serverMonitorVo = new ServerMonitorVo();
        serverMonitorVo.setServerCpuLogVoList(serverCpuLogBo.getServerCpuLogList(reqVo));
        serverMonitorVo.setServerLoadLogVoList(serverLoadLogBo.getServerLoadLogList(reqVo));
        serverMonitorVo.setServerMemoryLogVoList(serverMemoryLogBo.getServerMemoryLogList(reqVo));
        serverMonitorVo.setServerNetIOLogVoList(serverNetIOLogBo.getServerNetIOLogList(reqVo));
        return new ResBody().buildSuccessResBody(serverMonitorVo);
    }


    @ApiOperation(value = "添加服务器监控信息记录")
    @RequestMapping(value = "/saveServerMonitorInfo", method = RequestMethod.POST, consumes = "application/json")
    public ResBody saveServerMonitorInfo(@RequestBody String paramBean) {
        JSONObject agentJsonObject = (JSONObject) JSON.parse(paramBean);
        JSONObject cpuState = agentJsonObject.getJSONObject("cpuState");
        JSONObject memState = agentJsonObject.getJSONObject("memState");
        JSONObject sysLoadState = agentJsonObject.getJSONObject("sysLoadState");
        JSONObject systemInfo = agentJsonObject.getJSONObject("systemInfo");
        JSONObject netIoState = agentJsonObject.getJSONObject("netIoState");
        if (cpuState != null) {
            ServerCpuLogDo serverCpuLogDo = JSON.toJavaObject(cpuState, ServerCpuLogDo.class);
            serverCpuLogBo.saveCpuLog(serverCpuLogDo);
        }
        if (memState != null) {
            ServerMemoryLogDo serverMemoryLogDo = JSON.toJavaObject(memState, ServerMemoryLogDo.class);
            serverMemoryLogBo.saveMemoryLog(serverMemoryLogDo);
        }
        if (sysLoadState != null) {
            ServerLoadLogDo serverLoadLogDo = JSON.toJavaObject(sysLoadState, ServerLoadLogDo.class);
            serverLoadLogBo.saveLoadLog(serverLoadLogDo);
        }
        if (systemInfo != null) {
            ServerSystemInfoDo serverSystemInfoDo = JSON.toJavaObject(systemInfo, ServerSystemInfoDo.class);
            serverSystemInfoBo.saveSystemInfoBoLog(serverSystemInfoDo);
        }
        if (netIoState != null) {
            ServerNetIOLogDo serverNetIOLogDo = JSON.toJavaObject(netIoState, ServerNetIOLogDo.class);
            serverNetIOLogBo.saveNetIOLog(serverNetIOLogDo);
        }
        return new ResBody().buildSuccessResBody();
    }
}
