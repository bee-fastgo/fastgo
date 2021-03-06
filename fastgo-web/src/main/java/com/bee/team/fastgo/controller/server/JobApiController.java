package com.bee.team.fastgo.controller.server;


import com.bee.team.fastgo.server.core.conf.SimpleJobAdminConfig;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerExecutorLogBo;
import com.spring.simple.development.core.annotation.base.NoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import com.bee.team.fastgo.job.core.biz.model.RegistryParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.util.GsonTool;
import com.bee.team.fastgo.job.core.util.SimpleJobRemotingUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author luke
 * @date 17/5/10
 */
@Controller
@RequestMapping("/api")
public class JobApiController {

    @Autowired
    private ServerBo serverBo;
    @Autowired
    private ServerExecutorLogBo serverExecutorLogBo;

    /**
     * api
     *
     * @param uri
     * @param data
     * @return
     */
    @NoLogin
    @RequestMapping("/{uri}")
    @ResponseBody
    public ReturnT<String> api(HttpServletRequest request, @PathVariable("uri") String uri, @RequestBody(required = false) String data) {

        // valid
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "invalid request, HttpMethod not support.");
        }
        if (uri == null || uri.trim().length() == 0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "invalid request, uri-mapping empty.");
        }
        if (SimpleJobAdminConfig.getAdminConfig().getAccessToken() != null
                && SimpleJobAdminConfig.getAdminConfig().getAccessToken().trim().length() > 0
                && !SimpleJobAdminConfig.getAdminConfig().getAccessToken().equals(request.getHeader(SimpleJobRemotingUtil.XXL_JOB_ACCESS_TOKEN))) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "The access token is wrong.");
        }

        // services mapping
        if ("callback".equals(uri)) {
            List<HandleCallbackParam> callbackParamList = GsonTool.fromJson(data, List.class, HandleCallbackParam.class);
            return serverExecutorLogBo.callback(callbackParamList);
        } else if ("registry".equals(uri)) {
            RegistryParam registryParam = GsonTool.fromJson(data, RegistryParam.class);
            return serverBo.registry(registryParam);
        } else if ("registryRemove".equals(uri)) {
            RegistryParam registryParam = GsonTool.fromJson(data, RegistryParam.class);
            return serverBo.registryRemove(registryParam);
        } else {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "invalid request, uri-mapping(" + uri + ") not found.");
        }

    }

}
