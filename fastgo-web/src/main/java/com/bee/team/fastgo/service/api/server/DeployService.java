package com.bee.team.fastgo.service.api.server;

import com.bee.team.fastgo.service.api.server.dto.req.SimpleDeployDTO;
import com.bee.team.fastgo.tools.deploy.DeployDTO;
import com.spring.simple.development.support.exception.GlobalException;

/**
 * @author luke
 * @desc 部署项目
 * @date 2020-07-28
 **/
public interface DeployService {
    /**
     * 部署simple服务
     * @param simpleDeployDTO
     */
    void deploySimple(SimpleDeployDTO simpleDeployDTO) throws GlobalException;
}
