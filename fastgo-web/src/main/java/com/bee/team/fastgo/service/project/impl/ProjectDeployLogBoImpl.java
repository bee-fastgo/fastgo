package com.bee.team.fastgo.service.project.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ProjectDeployLogDoMapperExt;
import com.bee.team.fastgo.model.ProjectDeployLogDo;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.service.project.ProjectDeployLogBo;
import com.bee.team.fastgo.utils.StringUtil;
import com.bee.team.fastgo.vo.project.ProjectDeployResVo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bee.team.fastgo.constant.ProjectConstant.PROJECT_STATUS4;

@Service
public class ProjectDeployLogBoImpl extends AbstractLavaBoImpl<com.bee.team.fastgo.model.ProjectDeployLogDo, ProjectDeployLogDoMapperExt, com.bee.team.fastgo.model.ProjectDeployLogDoExample> implements ProjectDeployLogBo {

    @Autowired
    public BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ProjectDeployLogDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public String addProjectDeployLog(ProjectDeployLogDo projectDeployLogDo, UserDo userDo) {
        //插入项目日志
        projectDeployLogDo.setUser(userDo.getUserName());
        String deployId = StringUtil.getRandomUUID();
        projectDeployLogDo.setDeployLogId(deployId);
        projectDeployLogDo.setProjectDeployStatus(PROJECT_STATUS4.toString());
        mapper.insertSelective(projectDeployLogDo);
        return deployId;
    }

    @Override
    public List<ProjectDeployResVo> queryProjectDeployList(String projectCode) {
        return mapper.findProjectDeployList(projectCode);
    }
}