package com.bee.team.fastgo.mapper;

import com.alibaba.lava.base.LavaMapper;
import com.bee.team.fastgo.model.ProjectDeployLogDo;
import com.bee.team.fastgo.model.ProjectDeployLogDoExample;
import com.bee.team.fastgo.vo.project.ProjectDeployResVo;

import java.util.List;

public interface ProjectDeployLogDoMapperExt extends com.alibaba.lava.base.LavaMapper<com.bee.team.fastgo.model.ProjectDeployLogDo, com.bee.team.fastgo.model.ProjectDeployLogDoExample> {
    /**
     * @param projectCode
     * @return {@link List< ProjectDeployResVo>}
     * @author hs
     * @date 2020/8/5
     * @desc 查询项目部署记录
     */

    List<ProjectDeployResVo> findProjectDeployList(String projectCode);
}