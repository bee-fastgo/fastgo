package com.bee.team.fastgo.service.project;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.model.ProjectDoExample;
import com.bee.team.fastgo.vo.project.req.*;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

/**
 * @description:
 * @author: hs
 * @time: 2020/7/17 0017 10:32
 */
public interface ProjectBo extends LavaBo<ProjectDo, ProjectDoExample> {

    /**
     * 查询后台项目信息
     * @return
     */
    ResPageDTO queryBackProjectInfo(QueryProjectListVo queryProjectListVo);

    /**
     * 新增后台项目
     * @param insertBackProjectVo
     */
    void addBackProjectInfo(InsertBackProjectVo insertBackProjectVo);

    /**
     * 新增项目环境
     * @param insertBackProjectProfileVo
     */
    Integer addBackProjectProfile(InsertBackProjectProfileVo insertBackProjectProfileVo);

    /**
     * 查询项目日志
     * @param queryProjectLogVo
     * @return
     */
    String findProjectLog(QueryProjectLogVo queryProjectLogVo);

    /**
     * 查询前台项目信息
     * @return
     */
    ResPageDTO queryFrontProjectInfo(QueryProjectListVo queryProjectListVo);

    /**
     * 添加前台项目
     * @param insertBackProjectVo
     */
    void addFrontProjectInfo(InsertBackProjectVo insertBackProjectVo);

    /**
     * 部署后台项目
     * @param deployBackPorjectVo
     * @return
     */
    String execDeployBackProject(DeployBackPorjectVo deployBackPorjectVo);

    /**
     * 修改项目状态接口
     * @param updateProjectStatusVo
     */
    void updateProjectStatus(UpdateProjectStatusVo updateProjectStatusVo);
}
