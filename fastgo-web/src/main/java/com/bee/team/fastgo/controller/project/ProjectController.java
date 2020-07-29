package com.bee.team.fastgo.controller.project;

import com.bee.team.fastgo.service.project.ProjectBo;
import com.bee.team.fastgo.vo.project.*;
import com.bee.team.fastgo.vo.project.req.*;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.annotation.base.swagger.ApiImplicitParam;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: luke
 * @time: 2020/7/17 0017 10:32
 */
@Api(tags = "项目相关")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectBo projectBo;

    /**
     * @param queryProjectListVo
     * @return {@link ResBody< ProjectListVo>}
     * @author hs
     * @date 2020/7/25
     * @desc 后台项目列表
     */
    @RequestMapping(value = "/backEnd/projectList", method = RequestMethod.POST)
    @ApiOperation(value = "后台项目展示")
    @ValidHandler(key = "queryProjectListVo", value = QueryProjectListVo.class, isReqBody = false)
    public ResBody<ProjectListVo> backPorjectList(@RequestBody QueryProjectListVo queryProjectListVo) {
        ResPageDTO resPageDTO = projectBo.queryBackProjectInfo(queryProjectListVo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

    /**
     * @param insertBackProjectVo
     * @return {@link ResBody< Void>}
     * @author hs
     * @date 2020/7/25
     * @desc 后台项目新增
     */
    @RequestMapping(value = "/backEnd/addProject", method = RequestMethod.POST)
    @ApiOperation(value = "后台项目新增")
    @ValidHandler(key = "insertBackProjectVo", value = InsertBackProjectVo.class, isReqBody = false)
    public ResBody<Void> addBackProject(@RequestBody InsertBackProjectVo insertBackProjectVo) {
        projectBo.addBackProjectInfo(insertBackProjectVo);
        return new ResBody().buildSuccessResBody();
    }

    /**
     * @param insertBackProjectProfileVo
     * @return {@link ResBody< Void>}
     * @author hs
     * @date 2020/7/25
     * @desc 后台项目新增环境
     */
    @RequestMapping(value = "/backEnd/addProjectProfile", method = RequestMethod.POST)
    @ApiOperation(value = "新增项目环境")
    @ValidHandler(key = "insertBackProjectProfileVo", value = InsertBackProjectProfileVo.class, isReqBody = false)
    public ResBody<Void> addBackProjectProfile(@RequestBody InsertBackProjectProfileVo insertBackProjectProfileVo) {
        projectBo.addBackProjectProfile(insertBackProjectProfileVo);
        return new ResBody().buildSuccessResBody();
    }

    /**
     * @param deployBackPorjectVo
     * @return {@link ResBody< String>}
     * @author hs
     * @date 2020/7/25
     * @desc 后台项目部署
     */
    @RequestMapping(value = "/backEnd/deployBackProject", method = RequestMethod.POST)
    @ApiOperation(value = "后台项目部署")
    @ValidHandler(key = "deployBackPorjectVo", value = DeployBackPorjectVo.class, isReqBody = false)
    public ResBody<Void> deployBackProject(@RequestBody DeployBackPorjectVo deployBackPorjectVo) {
        projectBo.execDeployBackProject(deployBackPorjectVo);
        return new ResBody().buildSuccessResBody();
    }

    /**
     * @param deployFrontPorjectVo
     * @return {@link ResBody< Void>}
     * @author hs
     * @date 2020/7/28
     * @desc 前台项目部署
     */

    @RequestMapping(value = "/frontEnd/deployFrontProject", method = RequestMethod.POST)
    @ApiOperation(value = "前台项目部署")
    @ValidHandler(key = "deployBackPorjectVo", value = DeployBackPorjectVo.class, isReqBody = false)
    public ResBody<Void> deployFrontProject(@RequestBody DeployFrontPorjectVo deployFrontPorjectVo) {
        projectBo.execDeployFrontProject(deployFrontPorjectVo);
        return new ResBody().buildSuccessResBody();
    }

    /**
     * @param queryProjectLogVo
     * @return {@link ResBody< String>}
     * @author hs
     * @date 2020/7/25
     * @desc 后台项目日志
     */
    @RequestMapping(value = "/backEnd/queryProjectLog", method = RequestMethod.POST)
    @ApiOperation(value = "查询项目日志")
    @ValidHandler(key = "queryProjectLogVo", value = QueryProjectLogVo.class, isReqBody = false)
    public ResBody<String> queryProjectLog(@RequestBody QueryProjectLogVo queryProjectLogVo) {
        String log = projectBo.findProjectLog(queryProjectLogVo);
        return new ResBody().buildSuccessResBody(log);
    }

    //6.后台项目服务监控信息


    /**
     * @param queryProjectListVo
     * @return {@link ResBody< ProjectListVo>}
     * @author hs
     * @date 2020/7/25
     * @desc 前台项目列表
     */
    @RequestMapping(value = "/frontEnd/projectList", method = RequestMethod.POST)
    @ApiOperation(value = "前台项目展示")
    @ValidHandler(key = "queryProjectListVo", value = QueryProjectListVo.class, isReqBody = false)
    public ResBody<ProjectListVo> frontPorjectList(@RequestBody QueryProjectListVo queryProjectListVo) {
        ResPageDTO resPageDTO = projectBo.queryFrontProjectInfo(queryProjectListVo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

    /**
     * @param insertFrontProjectVo
     * @return {@link ResBody< Void>}
     * @author hs
     * @date 2020/7/25
     * @desc 前台项目新增
     */
    @RequestMapping(value = "/frontEnd/addFrontproject", method = RequestMethod.POST)
    @ApiOperation(value = "前台项目新增")
    @ValidHandler(key = "insertFrontProjectVo", value = InsertFrontProjectVo.class, isReqBody = false)
    public ResBody<Void> addfrontPorject(@RequestBody InsertFrontProjectVo insertFrontProjectVo) {
        projectBo.addFrontProjectInfo(insertFrontProjectVo);
        return new ResBody().buildSuccessResBody();
    }


    /**
     * @param
     * @return {@link ResBody< SofrwateProfileListVo>}
     * @author hs
     * @date 2020/7/27
     * @desc 软件环境展示
     */
    @RequestMapping(value = "/querySoftwareProfile", method = RequestMethod.POST)
    @ApiOperation(value = "软件环境展示")
    public ResBody<SofrwateProfileListVo> queryProfile() {
        SofrwateProfileListVo vo = projectBo.queryAllSoftware();
        return new ResBody().buildSuccessResBody(vo);
    }

    /**
     * @param projectCode
     * @return {@link ResBody< String>}
     * @author hs
     * @date 2020/7/28
     * @desc 查询项目分支名称
     */
    @RequestMapping(value = "/queryBranchName", method = RequestMethod.POST)
    @ApiOperation(value = "查询项目分支名称")
    public ResBody<String> queryProjectBranch(@RequestBody String projectCode) {
        List<String> branchNames = projectBo.findProjectBranch(projectCode);
        return new ResBody().buildSuccessResBody(branchNames);
    }

    /**
     * @param
     * @return {@link ResBody< SofrwateProfileListVo>}
     * @author hs
     * @date 2020/7/27
     * @desc 运行环境展示
     */
    @RequestMapping(value = "/queryRunProfile", method = RequestMethod.POST)
    @ApiOperation(value = "运行环境展示")
    public ResBody<RunProfileListVo> queryRunProfile() {
        RunProfileListVo runProfileListVo = projectBo.findRunProfile();
        return new ResBody().buildSuccessResBody(runProfileListVo);
    }

    /**
     * @param autoDeployVo
     * @return {@link ResBody< Void>}
     * @author hs
     * @date 2020/7/28
     * @desc 自动部署开关
     */
    @RequestMapping(value = "/updateAutoDeploy", method = RequestMethod.POST)
    @ApiOperation(value = "自动部署开关")
    public ResBody<Void> updateAutoDeploy(@RequestBody AutoDeployVo autoDeployVo) {
        projectBo.updateProjectDeploy(autoDeployVo);
        return new ResBody().buildSuccessResBody();
    }

}
