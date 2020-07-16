package com.bee.team.fastgo.controller.server;

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
 *
 * @author luke
 * @date 17/5/10
 */
@Controller
@RequestMapping("/api")
public class JobTestController {

    @Resource
    private AdminBiz adminBiz;

    /**
     * api
     *
     * @param data
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public ReturnT<String> test(HttpServletRequest request, @RequestBody(required = false) String data) {
        JobTriggerPoolHelper.trigger(1, TriggerTypeEnum.MANUAL, -1, null, "md D:\\test", "127.0.0.1:8081");
        return null;
    }

}
