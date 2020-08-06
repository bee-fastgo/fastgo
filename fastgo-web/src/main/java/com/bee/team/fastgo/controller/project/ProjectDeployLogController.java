package com.bee.team.fastgo.controller.project;

import com.bee.team.fastgo.job.core.biz.model.LogResult;
import com.bee.team.fastgo.service.project.ProjectDeployLogBo;
import com.bee.team.fastgo.tools.log.DeployJobFileAppender;
import com.bee.team.fastgo.tools.log.DeployLogResult;
import com.bee.team.fastgo.vo.project.ProjectDeployResVo;
import com.bee.team.fastgo.vo.server.ServerExecutorLogVo;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @description:
 * @author: luke
 * @time: 2020/7/21 0021 8:51
 */
@Api(tags = "项目部署日志")
@RestController
@RequestMapping("/deployLogs")
public class ProjectDeployLogController {

    @Autowired
    private ProjectDeployLogBo projectDeployLogBo;

    @ApiOperation(value = "执行中查看日志")
    @RequestMapping(value = "/logDetailCat", method = RequestMethod.GET)
    public ResBody<LogResult> logDetailCat(@ApiParam(name = "logId", value = "任务ID") String logId, @ApiParam(name = "fromLineNum", value = "加载行数") Integer fromLineNum) throws Exception {
        if (logId == null || fromLineNum == null) {
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "参数为空");
        }
        String logFileName = PropertyConfigurer.getProperty("fastgo.project.logs.path")
                .concat(File.separator)
                .concat(logId)
                .concat(".log");
        DeployLogResult deployLogResult = DeployJobFileAppender.readLog(logFileName, fromLineNum);
        return new ResBody().buildSuccessResBody(deployLogResult);
    }

    @ApiOperation(value = "查看日志")
    @RequestMapping(value = "/logDetail", method = RequestMethod.GET)
    public ResBody<ServerExecutorLogVo> logDetail(@ApiParam(name = "logId", value = "部署ID") String logId) {
        if (logId == null) {
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "参数为空");
        }
        String logFileName = PropertyConfigurer.getProperty("fastgo.project.logs.path")
                .concat(File.separator)
                .concat(logId)
                .concat(".log");
        File logFileNameFile = new File(logFileName);
        String logData = DeployJobFileAppender.readLines(logFileNameFile);
        return new ResBody().buildSuccessResBody(logData);
    }

    @ApiOperation(value = "项目部署记录")
    @RequestMapping(value = "/deployList", method = RequestMethod.POST)
    public ResBody<List<ProjectDeployResVo>> projectDeployList(@RequestBody String projectCode) {
        if (StringUtils.isEmpty(projectCode)) {
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "参数为空");
        }
        List<ProjectDeployResVo> projectDeployResVos = projectDeployLogBo.queryProjectDeployList(projectCode);
        return new ResBody().buildSuccessResBody(projectDeployResVos);
    }
}
