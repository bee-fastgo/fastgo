package com.bee.team.fastgo.mapper;

import com.bee.team.fastgo.model.ProfileRunprofileRelationDo;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.service.api.server.dto.req.SimpleDeployDTO;
import com.bee.team.fastgo.vo.project.DeployInfoVo;
import com.bee.team.fastgo.vo.project.ProjectBranchAndAccessAddrVo;
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
    DeployInfoVo findDeployProjectInfo(String projectCode);

    /**
     * @param map
     * @return {@link String}
     * @author hs
     * @date 2020/7/30
     * @desc 获取项目配置中心code
     */

    String findProjectConfigCode(Map<String,Object> map);

    /**
     * @param map
     * @return {@link Integer}
     * @author hs
     * @date 2020/7/30
     * @desc 查询后台项目条数
     */

    Integer queryBackProjectTotal(Map<String,Object> map);

    /**
     * @param map
     * @return {@link Integer}
     * @author hs
     * @date 2020/7/30
     * @desc 查询前台项目条数
     */

    Integer queryFrontProjectTotal(Map<String,Object> map);

    /**
     * @param
     * @return {@link ProfileRunprofileRelationDo}
     * @author hs
     * @date 2020/7/30
     * @desc 查询项目运行环境
     */

    List<ProjectBranchAndAccessAddrVo> findProjectAccessAddr(String projectCode);
}