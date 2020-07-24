package com.bee.team.fastgo.mapper;

import com.bee.team.fastgo.vo.project.ProjectListVo;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@MapperScan
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

}