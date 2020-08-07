package com.bee.team.fastgo.context;

import com.bee.team.fastgo.mapper.ProjectDeployLogDoMapperExt;
import com.bee.team.fastgo.mapper.ProjectDoMapperExt;
import com.bee.team.fastgo.model.ProjectDeployLogDo;
import com.bee.team.fastgo.model.ProjectDeployLogDoExample;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.service.api.server.DeployService;
import com.bee.team.fastgo.service.api.server.dto.req.SimpleDeployDTO;
import com.bee.team.fastgo.service.api.server.dto.req.VueDeployDTO;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.bee.team.fastgo.constant.ProjectConstant.*;
import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;

/**
 * @author hs
 * @date 2020/7/29 11:32
 * @desc 项目部署监听器
 **/

@Component
@EnableAsync
public class DeployListen {

    private static final Logger logger = LogManager.getLogger(DeployListen.class);

    @Autowired
    private ProjectDoMapperExt projectDoMapperExt;

    @Autowired
    private ProjectDeployLogDoMapperExt projectDeployLogDoMapperExt;

    @Autowired
    private DeployService deployService;

    @Async
    @EventListener(DeployEvent.class)
    public void listener(DeployEvent deployEvent){
        //查询对应的部署日志
        ProjectDeployLogDoExample example = new ProjectDeployLogDoExample();
        SimpleDeployDTO simpleDeployDTO = deployEvent.getDeployDTO();
        VueDeployDTO vueDeployDTO = deployEvent.getVueDeployDTO();
        example.createCriteria().andDeployLogIdEqualTo(simpleDeployDTO == null ? vueDeployDTO.getDeployLogId() : simpleDeployDTO.getDeployLogId());
        List<ProjectDeployLogDo>  projectDeployLogDos = projectDeployLogDoMapperExt.selectByExample(example);
        if (CollectionUtils.isEmpty(projectDeployLogDos)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目部署记录不存在");
        }
        ProjectDeployLogDo projectDeployLogDo = projectDeployLogDos.get(0);
        Integer type = deployEvent.getType();
        ProjectDo projectDo = new ProjectDo();
        Integer id = deployEvent.getId();
        projectDo.setId(id.longValue());
        try{
            if (PROJECT_TYPE1.equals(type)){
                //前台项目部署
                VueDeployDTO deployDTO = deployEvent.getVueDeployDTO();
                deployService.deploySimple(deployDTO);
            }else if (PROJECT_TYPE2.equals(type)){
                //后台项目部署
                SimpleDeployDTO deployDTO = deployEvent.getDeployDTO();
                deployService.deploySimple(deployDTO);
            }
            //修改部署记录的部署状态
            projectDeployLogDo.setProjectDeployStatus(PROJECT_STATUS4.toString());
            projectDeployLogDoMapperExt.updateByPrimaryKeySelective(projectDeployLogDo);
            //修改项目状态
            projectDo.setProjectStatus(PROJECT_STATUS4.toString());
            projectDoMapperExt.updateByPrimaryKeySelective(projectDo);
        }catch (Exception e){
            logger.error("》》》》》》》》》》项目部署失败");
            //修改项目状态
            projectDo.setProjectStatus(PROJECT_STATUS7.toString());
            projectDoMapperExt.updateByPrimaryKeySelective(projectDo);
            //修改部署记录的部署状态
            projectDeployLogDo.setProjectDeployStatus(PROJECT_STATUS7.toString());
            projectDeployLogDoMapperExt.updateByPrimaryKeySelective(projectDeployLogDo);
        }
    }

}
