package com.bee.team.fastgo.service.project;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.model.ProjectDoExample;
import com.bee.team.fastgo.vo.project.RunProfileListVo;
import com.bee.team.fastgo.vo.project.SofrwateProfileListVo;
import com.bee.team.fastgo.vo.project.req.*;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.List;

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
    void addBackProjectProfile(InsertBackProjectProfileVo insertBackProjectProfileVo);

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
     * @param insertFrontProjectVo
     */
    void addFrontProjectInfo(InsertFrontProjectVo insertFrontProjectVo);

    /**
     * 部署后台项目
     * @param deployBackPorjectVo
     * @return
     */
    void execDeployBackProject(DeployBackPorjectVo deployBackPorjectVo);

    /**
     * 修改项目状态接口
     * @param updateProjectStatusVo
     */
    void updateProjectStatus(UpdateProjectStatusVo updateProjectStatusVo);

    /**
     * @param
     * @return {@link List< String>}
     * @author hs
     * @date 2020/7/27
     * @desc 查询项目分支信息
     */
    List<String> findProjectBranch(String projectCode);

    /**
     * @param deployFrontPorjectVo
     * @return
     * @author hs
     * @date 2020/7/28
     * @desc 部署前台项目
     */
    void execDeployFrontProject(DeployFrontPorjectVo deployFrontPorjectVo);

    /**
     * @param autoDeployVo
     * @return
     * @author hs
     * @date 2020/7/28
     * @desc 自动部署开关
     */
    void updateProjectDeploy(AutoDeployVo autoDeployVo);

    /**
     * @param
     * @return {@link SofrwateProfileListVo}
     * @author hs
     * @date 2020/7/28
     * @desc 查询所有软件环境信息
     */

    SofrwateProfileListVo queryAllSoftware();

    /**
     * @param
     * @return
     * @author hs
     * @date 2020/7/29
     * @desc 查询所有的运行环境
     */

    RunProfileListVo findRunProfile();
}
