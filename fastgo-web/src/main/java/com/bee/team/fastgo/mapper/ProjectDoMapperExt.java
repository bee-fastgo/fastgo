package com.bee.team.fastgo.mapper;

import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.service.api.server.dto.req.SimpleDeployDTO;
import com.bee.team.fastgo.vo.project.ProjectListVo;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

public interface ProjectDoMapperExt extends com.alibaba.lava.base.LavaMapper<com.bee.team.fastgo.model.ProjectDo, com.bee.team.fastgo.model.ProjectDoExample> {

    /**
     * 查询后台项目信息
     * @return
     */
    List<ProjectListVo> findBackPorjectList(Map<String,Object> map);

    /**
     * 查询前台项目列表
     * @return
     */
    List<ProjectListVo> findFrontPorjectList(Map<String,Object> map);

    /**
     * 查询项目信息
     * @param map
     * @return
     */
    ProjectDo queryProjectInfo(Map<String, Object> map);

    /**
     * @param projectCode
     * @return {@link SimpleDeployDTO}
     * @author hs
     * @date 2020/7/28
     * @desc 查询部署所需项目信息
     */
    SimpleDeployDTO findDeployProjectInfo(String projectCode);
}