package com.bee.team.fastgo.mapper;

import com.alibaba.lava.base.LavaMapper;
import com.bee.team.fastgo.model.ProjectDeployLogDo;
import com.bee.team.fastgo.model.ProjectDeployLogDoExample;
import com.bee.team.fastgo.vo.project.ProjectDeployResVo;
import com.bee.team.fastgo.vo.project.req.ProjectDeployListVo;

import java.util.List;
import java.util.Map;

public interface ProjectDeployLogDoMapperExt extends com.alibaba.lava.base.LavaMapper<com.bee.team.fastgo.model.ProjectDeployLogDo, com.bee.team.fastgo.model.ProjectDeployLogDoExample> {
    /**
     * @param map
     * @return {@link List< ProjectDeployResVo>}
     * @author hs
     * @date 2020/8/5
     * @desc 查询项目部署记录
     */

    List<ProjectDeployResVo> findProjectDeployList(Map<String,Object> map);

    /**
     * @param map
     * @return {@link List< ProjectDeployResVo>}
     * @author hs
     * @date 2020/8/5
     * @desc 查询项目部署记录条数
     */
    Integer findProjectDeployListCount(Map<String,Object> map);
}