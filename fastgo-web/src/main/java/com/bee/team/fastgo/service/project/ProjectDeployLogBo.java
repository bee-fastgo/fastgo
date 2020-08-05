package com.bee.team.fastgo.service.project;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ProjectDeployLogDo;
import com.bee.team.fastgo.model.ProjectDeployLogDoExample;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.vo.project.req.DeployFrontPorjectVo;

public interface ProjectDeployLogBo extends com.alibaba.lava.base.LavaBo<com.bee.team.fastgo.model.ProjectDeployLogDo, com.bee.team.fastgo.model.ProjectDeployLogDoExample> {

    /**
     * @param projectDeployLogDo
     * @param userDo
     * @return
     * @author hs
     * @date 2020/8/4
     * @desc 添加前台项目部署日志
     */

    String addProjectDeployLog(ProjectDeployLogDo projectDeployLogDo, UserDo userDo);
}