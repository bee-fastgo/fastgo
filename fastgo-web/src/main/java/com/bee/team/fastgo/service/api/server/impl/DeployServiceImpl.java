package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.job.core.log.SimpleJobFileAppender;
import com.bee.team.fastgo.model.ServerDo;
import com.bee.team.fastgo.service.api.server.DeployService;
import com.bee.team.fastgo.service.api.server.dto.req.SimpleDeployDTO;
import com.bee.team.fastgo.service.api.server.dto.req.VueDeployDTO;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.tools.deploy.DeployDTO;
import com.bee.team.fastgo.tools.deploy.DeployHandler;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.spring.simple.development.support.exception.GlobalResponseCode.SERVICE_FAILED;

/**
 * @author luke
 * @desc 部署项目
 * @date 2020-07-28
 **/
@Service
public class DeployServiceImpl implements DeployService {
    private static Logger logger = LoggerFactory.getLogger(DeployServiceImpl.class);

    @Autowired
    private BaseSupport baseSupport;
    @Autowired
    private ServerBo serverBo;

    @Override
    public void deploySimple(SimpleDeployDTO simpleDeployDTO) {
        try {
            ServerDo serverDo = serverBo.getServerDoByIp(simpleDeployDTO.getServiceIp());
            DeployDTO deployDTO = baseSupport.objectCopy(simpleDeployDTO, DeployDTO.class);
            deployDTO.setServicePort(serverDo.getSshPort());
            deployDTO.setServiceUserName(serverDo.getSshUser());
            deployDTO.setServiceUserPassword(serverDo.getSshPassword());
            deployDTO.setDeployLogId(simpleDeployDTO.getDeployLogId());
            DeployHandler.deploy(deployDTO);
        } catch (Exception e) {
            logger.error("simple部署失败", e);
            throw new GlobalException(SERVICE_FAILED);
        }
    }

    @Override
    public void deploySimple(VueDeployDTO vueDeployDTO) throws GlobalException {
        try {
            ServerDo serverDo = serverBo.getServerDoByIp(vueDeployDTO.getServiceIp());
            DeployDTO deployDTO = baseSupport.objectCopy(vueDeployDTO, DeployDTO.class);
            deployDTO.setServicePort(serverDo.getSshPort());
            deployDTO.setServiceUserName(serverDo.getSshUser());
            deployDTO.setServiceUserPassword(serverDo.getSshPassword());
            deployDTO.setSimpleServiceUrl(vueDeployDTO.getServiceUrl());
            deployDTO.setDeployLogId(vueDeployDTO.getDeployLogId());
            DeployHandler.deployVue(deployDTO);
        } catch (Exception e) {
            logger.error("vue部署失败", e);
            throw new GlobalException(SERVICE_FAILED);
        }
    }
}
