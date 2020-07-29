package com.bee.team.fastgo.context;

import com.bee.team.fastgo.mapper.ProjectDoMapperExt;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.service.api.server.DeployService;
import com.bee.team.fastgo.service.api.server.dto.req.SimpleDeployDTO;
import com.bee.team.fastgo.service.api.server.dto.req.VueDeployDTO;
import com.bee.team.fastgo.tools.deploy.DeployDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import static com.bee.team.fastgo.constant.ProjectConstant.*;

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
    private DeployService deployService;

    @Async
    @EventListener(DeployEvent.class)
    public void listener(DeployEvent deployEvent){
        Integer type = deployEvent.getType();
        try{
            if (PROJECT_TYPE1.equals(type)){
                VueDeployDTO deployDTO = deployEvent.getVueDeployDTO();
                deployService.deploySimple(deployDTO);
            }else if (PROJECT_TYPE2.equals(type)){
                SimpleDeployDTO deployDTO = deployEvent.getDeployDTO();
                deployService.deploySimple(deployDTO);
            }
        }catch (Exception e){
            logger.error("项目部署失败");
        }
        //修改项目状态
        ProjectDo projectDo = new ProjectDo();
        Integer id = deployEvent.getId();
        projectDo.setId(id.longValue());
        projectDo.setProjectStatus(PROJECT_STATUS4.toString());
        projectDoMapperExt.updateByPrimaryKeySelective(projectDo);
    }

}
