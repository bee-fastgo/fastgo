package com.bee.team.fastgo.service.project.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ProjectDeployLogDoMapperExt;
import com.bee.team.fastgo.model.ProjectDeployLogDo;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.service.project.ProjectDeployLogBo;
import com.bee.team.fastgo.utils.StringUtil;
import com.bee.team.fastgo.vo.project.ProjectDeployResVo;
import com.bee.team.fastgo.vo.project.req.ProjectDeployListVo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bee.team.fastgo.constant.ProjectConstant.PROJECT_STATUS3;
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
        projectDeployLogDo.setProjectDeployStatus(PROJECT_STATUS3.toString());
        mapper.insertSelective(projectDeployLogDo);
        return deployId;
    }

    @Override
    public ResPageDTO queryProjectDeployList(ProjectDeployListVo projectDeployListVo) {
        Pager<ProjectDeployResVo> pager = new Pager();
        Map<String,Object> map = new HashMap<>();
        map.put("projectCode",projectDeployListVo.getProjectCode());
        Integer count = mapper.findProjectDeployListCount(map);
        pager.setLimit(projectDeployListVo.getPageSize());
        pager.setPageNo(projectDeployListVo.getPageNum());
        pager.setTotal(count);
        if (count > 0){
            map.put("start",pager.getStart());
            map.put("limit",pager.getLimit());
            List<ProjectDeployResVo> projectDeployList = mapper.findProjectDeployList(map);
            pager.setData(projectDeployList);
        }
        return baseSupport.pagerCopy(pager,ProjectDeployResVo.class);
    }
}