package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.server.core.model.SimpleJobAddress;
import com.bee.team.fastgo.server.core.model.SimpleJobInfo;
import com.bee.team.fastgo.server.core.thread.JobTriggerPoolHelper;
import com.bee.team.fastgo.server.core.trigger.TriggerTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bee.team.fastgo.job.core.biz.AdminBiz;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luke
 * @date 17/5/10
 */
@Controller
@RequestMapping("/api")
public class JobTestController {

    /**
     * api
     *
     * @param data
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request, @RequestBody(required = false) String data) {
        String logId = SimpleExecutorCmd.executorOneIpCmd(GlueTypeEnum.GLUE_SHELL, "ls /data", null, -1, "172.22.5.77");
        return logId;
    }

}
