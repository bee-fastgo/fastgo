package com.bee.team.fastgo.service.project.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.constant.ProjectConstant;
import com.bee.team.fastgo.context.DeployEvent;
import com.bee.team.fastgo.context.DeployPublisher;
import com.bee.team.fastgo.context.ProjectEvent;
import com.bee.team.fastgo.context.ProjectPublisher;
import com.bee.team.fastgo.dao.ProjectDao;
import com.bee.team.fastgo.job.core.util.IpUtil;
import com.bee.team.fastgo.mapper.*;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.project.gitlab.GitlabAPI;
import com.bee.team.fastgo.project.model.GitlabBranch;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.bee.team.fastgo.service.api.server.DeployService;
import com.bee.team.fastgo.service.api.server.SourceApi;
import com.bee.team.fastgo.service.api.server.dto.req.SimpleDeployDTO;
import com.bee.team.fastgo.service.api.server.dto.req.VueDeployDTO;
import com.bee.team.fastgo.service.api.server.dto.res.ResSourceListDTO;
import com.bee.team.fastgo.service.project.ProjectBo;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.utils.StringUtil;
import com.bee.team.fastgo.vo.project.*;
import com.bee.team.fastgo.vo.project.req.*;
import com.bee.team.fastgo.vo.server.ServerVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.utils.Pager;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bee.team.fastgo.constant.ProjectConstant.*;
import static com.spring.simple.development.support.exception.ResponseCode.*;


@Service
public class ProjectBoImpl extends AbstractLavaBoImpl<ProjectDo, ProjectDoMapperExt, ProjectDoExample> implements ProjectBo {

    @Value("${server.port}")
    private Integer port;

    @Value("${gitlab.url}")
    private String gitUrl;

    @Value("${project.publicTemplate}")
    private String publicTemplate;

    @Value("${frontTemplate}")
    private String frontTemplate;

    @Value("${gitlab.normal.privateToken}")
    private String privateToken;

    @Autowired
    public void setBaseMapper(ProjectDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ServerBo serverBo;

    @Autowired
    private SourceApi sourceApi;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectPublisher projectPublisher;

    @Autowired
    private DeployPublisher deployPublisher;

    @Override
    public ResPageDTO<ProjectListVo> queryBackProjectInfo(QueryProjectListVo queryProjectListVo,UserDo userDo) {
        Pager<ProjectListVo> pager = new Pager<>();
        //查询数据条数
        Map<String,Object> map = new HashMap<>();
        map.put("projectName",queryProjectListVo.getProjectName());
        if (!StringUtils.isEmpty(queryProjectListVo.getProjectName())){
            String projectName = "%"+queryProjectListVo.getProjectName()+"%";
            map.put("projectName",projectName);
        }
        map.put("projectStatus",queryProjectListVo.getProjectStatus());
        if (!ObjectUtils.isEmpty(userDo) && !userDo.getUserName().equals(ADMIN_USER)){
            map.put("userId",userDo.getId().intValue());
        }
        Integer count = mapper.queryBackProjectTotal(map);
        pager.setTotal(count);
        pager.setPageNo(queryProjectListVo.getPageNum());
        pager.setLimit(queryProjectListVo.getPageSize());
        if (count > 0){
            map.put("start",pager.getStart());
            map.put("limit",pager.getLimit());
            List<ProjectListVo> projectListVoList = mapper.findBackPorjectList(map);
            //项目添加访问地址
            List<ProjectListVo> projectListVos = projectListVoList.stream().map(ProjectListVo -> {
                List<ProjectBranchAndAccessAddrVo> projectBranchAndAccessAddrVoList = mapper.findProjectAccessAddr(ProjectListVo.getProjectCode());
                List<ProjectBranchAndAccessAddrVo> projectBranchAndAccessAddrVos = projectBranchAndAccessAddrVoList.stream().map(ProjectBranchAndAccessAddrVo -> {
                    Map runMap = StringUtil.strToMap(ProjectBranchAndAccessAddrVo.getRunProfileConfig());
                    ProjectBranchAndAccessAddrVo.setAccessAddr("http://"+runMap.get("ip")+":"+runMap.get("port"));
                    return ProjectBranchAndAccessAddrVo;
                }).collect(Collectors.toList());
                ProjectListVo.setAccessAddrs(projectBranchAndAccessAddrVos);
                return ProjectListVo;
            }).collect(Collectors.toList());
            pager.setData(projectListVos);
        }
        return baseSupport.pagerCopy(pager,ProjectListVo.class);
    }

    @Override
    public void addBackProjectInfo(InsertBackProjectVo insertBackProjectVo) {
        //项目基本信息
        ProjectDo projectDo = baseSupport.objectCopy(insertBackProjectVo,ProjectDo.class);
        //定义项目code(项目名大写)
        String projectCode = StringUtil.getRandomUUID();
        projectDo.setProjectCode(projectCode);
        projectDo.setProjectType(ProjectConstant.PROJECT_TYPE2.toString());
        projectDo.setProjectStatus(PROJECT_STATUS1.toString());

        //配置环境
        InsertBackProjectProfileVo insertBackProjectProfileVo = baseSupport.objectCopy(insertBackProjectVo,InsertBackProjectProfileVo.class);
        insertBackProjectProfileVo.setProjectCode(projectCode);
        Integer flag = projectDao.addProjectProfile(insertBackProjectProfileVo);
        //设置项目当前状态
        projectDo.setProjectStatus(flag.toString());

        //生成后台项目模板
        if (StringUtils.isEmpty(insertBackProjectVo.getGitUrl())){
           //默认上传的项目文件路径
           String filePath = publicTemplate;
           //是否生成simple框架
           if (insertBackProjectVo.getIsConfirm().equals(IS_CONFIRM1)){
               //获取项目框架的生成路径
               filePath = projectDao.generateSimpleTemplate(projectDo);
               if (!StringUtils.isEmpty(filePath)){
                   System.out.println(filePath);
               }else {
                   throw new GlobalException(RES_DATA_NOT_EXIST,"自动生成代码框架失败");
               }
           }
            GitlabProjectDo gitlabProjectDo;
            try {
                //调取gitlab接口，创建gitlab后台项目
                GitlabAPI gitlabAPI = new GitlabAPI(gitUrl,privateToken);
                gitlabProjectDo = gitlabAPI.createNewProject(insertBackProjectVo.getProjectName(),insertBackProjectVo.getProjectDesc());
                if (ObjectUtils.isEmpty(gitlabProjectDo)){
                    throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
                }
            }catch (Exception e){
                throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
            }
            //上传后台项目模板代码到gitlab项目中
            projectDao.uploadBackCodeIntoGitlab(gitlabProjectDo,filePath);
            projectDo.setGitUrl(gitlabProjectDo.getHttpUrl());
        }
        mapper.insertSelective(projectDo);
    }

    @Override
    public void addBackProjectProfile(InsertBackProjectProfileVo insertBackProjectProfileVo) {
        projectDao.addProjectProfile(insertBackProjectProfileVo);
    }

    @Override
    public String findProjectLog(QueryProjectLogVo queryProjectLogVo) {
        // TODO: 2020/7/24 查询项目日志
        return null;
    }

    @Override
    public ResPageDTO<ProjectListVo> queryFrontProjectInfo(QueryProjectListVo queryProjectListVo,UserDo userDo) {
        Pager<ProjectListVo> pager = new Pager<>();
        Map<String,Object> map = new HashMap<>();
        //查询前台项目条数
        map.put("projectName",queryProjectListVo.getProjectName());
        if (!StringUtils.isEmpty(queryProjectListVo.getProjectName())){
            String projectName = "%"+queryProjectListVo.getProjectName()+"%";
            map.put("projectName",projectName);
        }
        map.put("projectStatus",queryProjectListVo.getProjectStatus());
        if (!ObjectUtils.isEmpty(userDo) && !userDo.getUserName().equals(ADMIN_USER)){
            map.put("userId",userDo.getId().intValue());
        }
        Integer count = mapper.queryFrontProjectTotal(map);
        pager.setTotal(count);
        pager.setPageNo(queryProjectListVo.getPageNum());
        pager.setLimit(queryProjectListVo.getPageSize());
        if (count > 0){
            map.put("start",pager.getStart());
            map.put("limit",pager.getLimit());
            List<ProjectListVo> projectListVoList = mapper.findFrontPorjectList(map);
            //获取部署后前台项目访问地址
            List<ProjectListVo> projectListVos = projectListVoList.stream().map(ProjectListVo -> {
                List<ProjectBranchAndAccessAddrVo> projectBranchAndAccessAddrVoList = mapper.findProjectAccessAddr(ProjectListVo.getProjectCode());
                List<ProjectBranchAndAccessAddrVo> projectBranchAndAccessAddrVos = projectBranchAndAccessAddrVoList.stream().map(ProjectBranchAndAccessAddrVo -> {
                    Map runMap = StringUtil.strToMap(ProjectBranchAndAccessAddrVo.getRunProfileConfig());
                    ProjectBranchAndAccessAddrVo.setAccessAddr("http://"+runMap.get("ip")+":"+runMap.get("port"));
                    return ProjectBranchAndAccessAddrVo;
                }).collect(Collectors.toList());
                ProjectListVo.setAccessAddrs(projectBranchAndAccessAddrVos);
                return ProjectListVo;
            }).collect(Collectors.toList());
            pager.setData(projectListVos);
        }
        return baseSupport.pagerCopy(pager,ProjectListVo.class);
    }

    @Override
    public void addFrontProjectInfo(InsertFrontProjectVo insertFrontProjectVo) {
        //项目基本信息
        ProjectDo projectDo = baseSupport.objectCopy(insertFrontProjectVo,ProjectDo.class);
        //定义项目code(项目名大写)
        String projectCode = StringUtil.getRandomUUID();
        projectDo.setProjectCode(projectCode);
        projectDo.setProjectType(ProjectConstant.PROJECT_TYPE1.toString());
        projectDo.setProjectStatus(PROJECT_STATUS1.toString());

        //配置环境
        InsertBackProjectProfileVo insertBackProjectProfileVo = baseSupport.objectCopy(insertFrontProjectVo,InsertBackProjectProfileVo.class);
        insertBackProjectProfileVo.setProjectCode(projectCode);
        Integer flag = projectDao.addProjectProfile(insertBackProjectProfileVo);
        projectDo.setProjectStatus(flag.toString());

        //生成前台项目模板
        if (StringUtils.isEmpty(insertFrontProjectVo.getGitUrl())){
            //创建新的gitlab后台项目
            GitlabProjectDo gitlabProjectDo;
            try {
                //调取gitlab接口，创建gitlab项目
                GitlabAPI gitlabAPI = new GitlabAPI(gitUrl,privateToken);
                gitlabProjectDo = gitlabAPI.createNewProject(insertFrontProjectVo.getProjectName(),insertFrontProjectVo.getProjectDesc());
                if (ObjectUtils.isEmpty(gitlabProjectDo)){
                    throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
                }
            }catch (Exception e){
                throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
            }
            //是否生成vue框架
            if (IS_CONFIRM0.equals(insertFrontProjectVo.getIsConfirm())){
                //上传项目默认文件到gitlab
                projectDao.uploadFrontCodeIntoGitlab(gitlabProjectDo, publicTemplate);
            }else if (IS_CONFIRM1.equals(insertFrontProjectVo.getIsConfirm())){
                //上传对应的前台项目模板代码到gitlab项目中
                if (insertFrontProjectVo.getProjectType().equals(FRONT_PROJECT_TEMPLATE1)){
                    projectDao.uploadFrontCodeIntoGitlab(gitlabProjectDo, frontTemplate+FRONT_PROJECT_TYPE1);
                }else if (insertFrontProjectVo.getProjectType().equals(FRONT_PROJECT_TEMPLATE2)){
                    projectDao.uploadFrontCodeIntoGitlab(gitlabProjectDo, frontTemplate+FRONT_PROJECT_TYPE2);
                }
            }
            projectDo.setGitUrl(gitlabProjectDo.getHttpUrl());
        }
        mapper.insertSelective(projectDo);
    }

    @Override
    public void execDeployBackProject(DeployBackPorjectVo deployBackPorjectVo,String deployId) {
        //获取项目信息
        ProjectDoExample example = new ProjectDoExample();
        example.createCriteria().andProjectCodeEqualTo(deployBackPorjectVo.getProjectCode());
        List<ProjectDo> projectDoList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(projectDoList)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目信息不存在");
        }
        ProjectDo projectDo = projectDoList.get(0);
        //项目只有创建完成，部署完成，部署失败，这三个状态才可以部署
        if (!PROJECT_STATUS2.toString().equals(projectDo.getProjectStatus()) && !PROJECT_STATUS4.toString().equals(projectDo.getProjectStatus()) && !PROJECT_STATUS7.toString().equals(projectDo.getProjectStatus())){
            throw new GlobalException(RES_ILLEGAL_OPERATION,"项目当前状态不能部署");
        }
        //获取项目运行环境信息
        DeployInfoVo vo = mapper.findDeployProjectInfo(deployBackPorjectVo.getProjectCode());
        Map map = StringUtil.strToMap(vo.getRunProfileConfig());
        if (map == null){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目运行环境不存在");
        }
        SimpleDeployDTO dto = baseSupport.objectCopy(vo,SimpleDeployDTO.class);
        dto.setBranchName(deployBackPorjectVo.getBranchName());
        String port = String.valueOf(map.get("port"));
        dto.setProjectPort(port);
        dto.setDeployLogId(deployId);
        //调取服务器部署项目脚本
        DeployEvent deployEvent = new DeployEvent(new Object(),dto,null,PROJECT_TYPE2,projectDo.getId().intValue());
        deployPublisher.publish(deployEvent);
        //修改项目状态为部署中
        projectDo.setProjectStatus(PROJECT_STATUS3.toString());
        mapper.updateByPrimaryKeySelective(projectDo);
    }

    /**
     * @param updateProjectStatusVo
     * @return
     * @author hs
     * @date 2020/7/25
     * @desc 服务方回调，修改项目状态
     */
    @Override
    public void updateProjectStatus(UpdateProjectStatusVo updateProjectStatusVo) {
        //获取当前项目信息
        Map<String,Object> map = new HashMap<>();
        map.put("code",updateProjectStatusVo.getCode());
        map.put("type",updateProjectStatusVo.getType());
        ProjectDo projectDo = mapper.queryProjectInfo(map);
        if (ObjectUtils.isEmpty(projectDo)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"未找到对应的项目信息");
        }
        //项目  软件环境部署成功+运行环境部署成功 = 项目创建完成
        if (OBJECT_TYPE1.equals(updateProjectStatusVo.getType())){
            //运行环境
            if (PROJECT_STATUS1.toString().equals(projectDo.getProjectStatus())){
                projectDo.setProjectStatus(PROJECT_STATUS6.toString());
            }else if (PROJECT_STATUS5.toString().equals(projectDo.getProjectStatus())){
                projectDo.setProjectStatus(PROJECT_STATUS2.toString());
            }
        }else if (OBJECT_TYPE2.equals(updateProjectStatusVo.getType())){
            //软件环境
            if (PROJECT_STATUS1.toString().equals(projectDo.getProjectStatus())){
                projectDo.setProjectStatus(PROJECT_STATUS5.toString());
            }else if (PROJECT_STATUS6.toString().equals(projectDo.getProjectStatus())){
                projectDo.setProjectStatus(PROJECT_STATUS2.toString());
            }
        }
        mapper.updateByPrimaryKeySelective(projectDo);
    }

    @Override
    public List<String> findProjectBranch(String projectCode) {
        //查询当前项目信息
        ProjectDoExample example = new ProjectDoExample();
        example.createCriteria().andProjectCodeEqualTo(projectCode);
        List<ProjectDo> projectDoList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(projectDoList)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目信息不存在");
        }
        ProjectDo projectDo = projectDoList.get(0);
        GitlabAPI gitlabAPI = new GitlabAPI(gitUrl,privateToken);
        //获取项目的gtilab id（该id为项目在gitlab中的id，并非数据库中项目的id）
        try{
            List<GitlabProjectDo> gitlibProjects = gitlabAPI.getAllProject();
            List<GitlabProjectDo> gitlibProjectList= gitlibProjects.stream().filter(r -> r.getName().equals(projectDo.getProjectName())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(gitlibProjectList)){
                throw new GlobalException(RES_DATA_NOT_EXIST,"gitlab项目信息不存在");
            }
            GitlabProjectDo gitlabProjectDo = gitlibProjectList.get(0);
            //获取项目的所有分支信息
            List<GitlabBranch> gitlabBranchList = gitlabAPI.queryAllBranchInfo(gitlabProjectDo.getId());
            List<String> branchNames = gitlabBranchList.stream().map(r -> r.getName()).collect(Collectors.toList());
            return branchNames;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void execDeployFrontProject(DeployFrontPorjectVo deployFrontPorjectVo,String deployId) {
        //部署项目
        ProjectDoExample example = new ProjectDoExample();
        example.createCriteria().andProjectCodeEqualTo(deployFrontPorjectVo.getProjectCode());
        List<ProjectDo> projectDoList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(projectDoList)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目信息不存在");
        }
        ProjectDo projectDo = projectDoList.get(0);
        //项目只有创建完成，部署完成，部署失败，这三个状态才可以部署
        if (!PROJECT_STATUS2.toString().equals(projectDo.getProjectStatus()) && !PROJECT_STATUS4.toString().equals(projectDo.getProjectStatus()) && !PROJECT_STATUS7.toString().equals(projectDo.getProjectStatus())){
            throw new GlobalException(RES_ILLEGAL_OPERATION,"项目当前状态不能部署");
        }
        //获取项目运行环境信息
        DeployInfoVo vo = mapper.findDeployProjectInfo(deployFrontPorjectVo.getProjectCode());
        Map<String,String> map = StringUtil.strToMap(vo.getRunProfileConfig());
        if (map == null){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目运行环境不存在");
        }
        VueDeployDTO deployDTO = baseSupport.objectCopy(vo,VueDeployDTO.class);
        deployDTO.setBranchName(deployFrontPorjectVo.getBranchName());
        String port = String.valueOf(map.get("port"));
        deployDTO.setProjectPort(port);
        deployDTO.setServiceUrl(deployFrontPorjectVo.getServiceUrl());
        deployDTO.setDeployLogId(deployId);
        //调取服务器部署项目脚本
        DeployEvent deployEvent = new DeployEvent(new Object(),null,deployDTO,PROJECT_TYPE1,projectDo.getId().intValue());
        deployPublisher.publish(deployEvent);
        //修改项目状态为部署中
        projectDo.setProjectStatus(PROJECT_STATUS3.toString());
        mapper.updateByPrimaryKeySelective(projectDo);
    }

    @Override
    public void updateProjectDeploy(AutoDeployVo autoDeployVo) {
        ProjectDoExample example = new ProjectDoExample();
        example.createCriteria().andProjectCodeEqualTo(autoDeployVo.getProjectCode());
        List<ProjectDo> projectDoList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(projectDoList)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目信息不存在");
        }
        ProjectDo projectDo = projectDoList.get(0);
        projectDo.setId(autoDeployVo.getId().longValue());
        String projectCode = autoDeployVo.getProjectCode();
        if (AUTO_DEPLOY0.equals(autoDeployVo.getAutoDeploy())){
            //开启自动部署
            projectDo.setAutoDeploy(AUTO_DEPLOY1);
            //事件添加webhook
            ProjectEvent projectEvent = null;
            if (PROJECT_TYPE1.toString().equals(projectDo.getProjectType())){
                projectEvent = new ProjectEvent(new Object(),projectCode,"http://"+ IpUtil.getIp() +":"+port+"/project/frontEnd/deployFrontProject",AUTO_DEPLOY1);
            }else if (PROJECT_TYPE2.toString().equals(projectDo.getProjectType())){
                projectEvent = new ProjectEvent(new Object(),projectCode,"http://"+ IpUtil.getIp() +":"+port+"/project/backEnd/deployBackProject",AUTO_DEPLOY1);
            }
            projectPublisher.publish(projectEvent);
        }else if (AUTO_DEPLOY1.equals(autoDeployVo.getAutoDeploy())){
            //关闭自动部署
            projectDo.setAutoDeploy(AUTO_DEPLOY0);
            //事件删除webhook
            ProjectEvent projectEvent = new ProjectEvent(new Object(),projectCode,"",AUTO_DEPLOY0);
            projectPublisher.publish(projectEvent);
        }
        mapper.updateByPrimaryKeySelective(projectDo);
    }

    @Override
    public SofrwateProfileListVo queryAllSoftware() {
        //调取服务接口，查询所有服务信息
        List<ResSourceListDTO> dtos = sourceApi.getSourceList();
        SofrwateProfileListVo vo = new SofrwateProfileListVo();
        vo.setResSourceListDTOList(dtos);
        return vo;
    }

    @Override
    public RunProfileListVo findRunProfile() {
        //调取服务器接口，查询支持项目运行的服务器信息
        List<ServerVo> serverVoList = serverBo.queryListServer();
        List<String> list = serverVoList.stream().map(ServerVo::getServerIp).collect(Collectors.toList());
        RunProfileListVo runProfileListVo = new RunProfileListVo();
        runProfileListVo.setIps(list);
        return runProfileListVo;
    }

    @Override
    public String getProjectStatus(String projectCode) {
        //查询项目状态，通过项目唯一标识code
        ProjectDoExample example = new ProjectDoExample();
        example.createCriteria().andProjectCodeEqualTo(projectCode);
        List<ProjectDo> projectDoList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(projectDoList)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目信息不存在");
        }
        ProjectDo projectDo = projectDoList.get(0);
        return projectDo.getProjectStatus();
    }

    @Override
    public ProjectInfoResVo getProjectInfoByCode(String projectCode) {
        if (StringUtils.isEmpty(projectCode)){
            throw new GlobalException(RES_PARAM_IS_EMPTY,"项目唯一标识不能为空");
        }
        ProjectInfoResVo projectInfoResVo = mapper.findProjectDetail(projectCode);
        //查询项目基础信息及访问地址
        List<ProjectBranchAndAccessAddrVo> projectBranchAndAccessAddrVoList = mapper.findProjectAccessAddr(projectCode);
        List<ProjectBranchAndAccessAddrVo> projectBranchAndAccessAddrVos = projectBranchAndAccessAddrVoList.stream().map(ProjectBranchAndAccessAddrVo -> {
            Map runMap = StringUtil.strToMap(ProjectBranchAndAccessAddrVo.getRunProfileConfig());
            ProjectBranchAndAccessAddrVo.setAccessAddr("http://"+runMap.get("ip")+":"+runMap.get("port"));
            return ProjectBranchAndAccessAddrVo;
        }).collect(Collectors.toList());
        projectInfoResVo.setAccessAddrs(projectBranchAndAccessAddrVos);
        //查询项目成员
        List<GitlabUserResVo> gitlabUserResVos = mapper.findProjectMember(projectCode);
        projectInfoResVo.setGitlabUserResVos(gitlabUserResVos);
        return projectInfoResVo;
    }


}
