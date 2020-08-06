package com.bee.team.fastgo.controller.project;

import com.bee.team.fastgo.common.CommonLoginValue;
import com.bee.team.fastgo.job.core.util.IpUtil;
import com.bee.team.fastgo.model.ProjectDeployLogDo;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.service.project.ProjectBo;
import com.bee.team.fastgo.service.project.ProjectDeployLogBo;
import com.bee.team.fastgo.utils.FileUploadUtil;
import com.bee.team.fastgo.vo.project.*;
import com.bee.team.fastgo.vo.project.req.*;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @description:
 * @author: hs
 * @time: 2020/7/17 0017 10:32
 */
@Api(tags = "项目相关")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Value("${mysql.fileAddr}")
    private String fileAddr;

    @Value("${server.file.static.path}")
    private String mapPath;

    @Value("${server.port}")
    private String port;

    @Autowired
    public BaseSupport baseSupport;

    @Autowired
    private ProjectBo projectBo;

    @Autowired
    private ProjectDeployLogBo projectDeployLogBo;

    /**
     * @param queryProjectListVo
     * @return {@link ResBody<ProjectListVo>}
     * @author hs
     * @date 2020/7/25
     * @desc 后台项目列表
     */
    @RequestMapping(value = "/backEnd/projectList", method = RequestMethod.POST)
    @ApiOperation(value = "后台项目展示")
    @ValidHandler(key = "queryProjectListVo", value = QueryProjectListVo.class, isReqBody = false)
    public ResBody<ProjectListVo> backPorjectList(@RequestBody QueryProjectListVo queryProjectListVo, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        UserDo userDo = (UserDo) httpSession.getAttribute(CommonLoginValue.SESSION_LOGIN_KEY);
        ResPageDTO resPageDTO = projectBo.queryBackProjectInfo(queryProjectListVo, userDo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

    /**
     * @param insertBackProjectVo
     * @return {@link ResBody<Void>}
     * @author hs
     * @date 2020/7/25
     * @desc 后台项目新增
     */
    @RequestMapping(value = "/backEnd/addProject", method = RequestMethod.POST)
    @ApiOperation(value = "后台项目新增")
    @ValidHandler(key = "insertBackProjectVo", value = InsertBackProjectVo.class, isReqBody = false)
    public ResBody<Void> addBackProject(@RequestBody InsertBackProjectVo insertBackProjectVo) {
        if (CollectionUtils.isEmpty(insertBackProjectVo.getSoftwareInfoVos())) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "软件环境不能为空");
        }
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
    public ResBody<String> deployBackProject(@RequestBody DeployBackPorjectVo deployBackPorjectVo, HttpServletRequest request) {
        //添加部署日志
        HttpSession httpSession = request.getSession();
        UserDo userDo = (UserDo) httpSession.getAttribute(CommonLoginValue.SESSION_LOGIN_KEY);
        ProjectDeployLogDo projectDeployLogDo = baseSupport.objectCopy(deployBackPorjectVo, ProjectDeployLogDo.class);
        String deployId = projectDeployLogBo.addProjectDeployLog(projectDeployLogDo, userDo);
        //执行部署
        projectBo.execDeployBackProject(deployBackPorjectVo, deployId);
        return new ResBody().buildSuccessResBody(deployId);
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
    public ResBody<String> deployFrontProject(@RequestBody DeployFrontPorjectVo deployFrontPorjectVo, HttpServletRequest request) {
        //添加部署日志
        HttpSession httpSession = request.getSession();
        UserDo userDo = (UserDo) httpSession.getAttribute(CommonLoginValue.SESSION_LOGIN_KEY);
        ProjectDeployLogDo projectDeployLogDo = baseSupport.objectCopy(deployFrontPorjectVo, ProjectDeployLogDo.class);
        String deployId = projectDeployLogBo.addProjectDeployLog(projectDeployLogDo, userDo);
        //执行部署
        projectBo.execDeployFrontProject(deployFrontPorjectVo, deployId);
        return new ResBody().buildSuccessResBody(deployId);
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
    public ResBody<ProjectListVo> frontPorjectList(@RequestBody QueryProjectListVo queryProjectListVo, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        UserDo userDo = (UserDo) httpSession.getAttribute(CommonLoginValue.SESSION_LOGIN_KEY);
        ResPageDTO resPageDTO = projectBo.queryFrontProjectInfo(queryProjectListVo, userDo);
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

    /**
     * @param projectCode
     * @return {@link ResBody< String>}
     * @author hs
     * @date 2020/7/30
     * @desc 获取项目状态
     */
    @RequestMapping(value = "/getProjectStatus", method = RequestMethod.POST)
    @ApiOperation(value = "获取项目状态")
    public ResBody<String> getProjectStatus(@RequestBody String projectCode) {
        if (StringUtils.isEmpty(projectCode)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "项目code不能为空");
        }
        String status = projectBo.getProjectStatus(projectCode);
        return new ResBody().buildSuccessResBody(status);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "sql文件上传")
    @ApiImplicitParam(name = "file", value = "文件", dataTypeClass = String.class)
    public ResBody uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "上传的文件为空");
        }
        //定义要上传文件 的存放路径
        String localPath = fileAddr;
        //获得文件名字
        String fileName = file.getOriginalFilename();
        if (FileUploadUtil.upload(file, localPath, fileName)) {
            // 得到去掉了uri的路径
            String url = request.getScheme() + "://" + IpUtil.getIp() + ":" + port + request.getContextPath() + mapPath + fileName;
            System.out.println(url);
            return new ResBody().buildSuccessResBody(url);
        }
        return ResBody.buildFailResBody();
    }

    @RequestMapping(value = "/getProjectInfoByCode", method = RequestMethod.POST)
    @ApiOperation(value = "根据项目code查询项目信息")
    public ResBody<ProjectInfoResVo> getProjectInfoByCode(@RequestBody String projectCode){
        ProjectInfoResVo projectInfoResVo = projectBo.getProjectInfoByCode(projectCode);
        return new ResBody().buildSuccessResBody(projectInfoResVo);
    }

}
