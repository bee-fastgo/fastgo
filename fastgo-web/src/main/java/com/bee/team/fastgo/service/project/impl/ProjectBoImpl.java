package com.bee.team.fastgo.service.project.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.constant.ProjectConstant;
import com.bee.team.fastgo.context.DeployEvent;
import com.bee.team.fastgo.context.DeployPublisher;
import com.bee.team.fastgo.context.ProjectEvent;
import com.bee.team.fastgo.context.ProjectPublisher;
import com.bee.team.fastgo.dao.ProjectDao;
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
import com.bee.team.fastgo.vo.project.*;
import com.bee.team.fastgo.vo.project.req.*;
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

    @Value("${project.publicTemplate}")
    private String publicTemplate;

    @Value("${frontTemplate}")
    private String frontTemplate;

    @Autowired
    public void setBaseMapper(ProjectDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private SourceApi sourceApi;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private DeployService deployService;

    @Autowired
    private ProjectPublisher projectPublisher;

    @Autowired
    private DeployPublisher deployPublisher;


    @Override
    public ResPageDTO<ProjectListVo> queryBackProjectInfo(QueryProjectListVo queryProjectListVo) {
        Pager<ProjectListVo> pager = new Pager<>();
        pager.setPageNo(queryProjectListVo.getPageNum());
        pager.setLimit(queryProjectListVo.getPageSize());
        Map<String,Object> map = new HashMap<>();
        map.put("start",pager.getStart());
        map.put("limit",pager.getLimit());
        List<ProjectListVo> projectListVoList = mapper.findBackPorjectList(map);
        pager.setData(projectListVoList);
        return baseSupport.pagerCopy(pager,ProjectListVo.class);
    }

    @Override
    public void addBackProjectInfo(InsertBackProjectVo insertBackProjectVo) {
        //项目基本信息
        ProjectDo projectDo = baseSupport.objectCopy(insertBackProjectVo,ProjectDo.class);
        //定义项目code(项目名大写)
        String projectCode = insertBackProjectVo.getProjectName().toUpperCase();
        projectDo.setProjectCode(projectCode);
        projectDo.setProjectType(ProjectConstant.PROJECT_TYPE2.toString());
        projectDo.setProjectStatus(PROJECT_STATUS1.toString());

        //配置环境
        InsertBackProjectProfileVo insertBackProjectProfileVo = baseSupport.objectCopy(insertBackProjectVo,InsertBackProjectProfileVo.class);
        insertBackProjectProfileVo.setProjectCode(projectCode);
        Integer flag = projectDao.addProjectProfile(insertBackProjectProfileVo);
        projectDo.setProjectStatus(flag.toString());

        //生成后台项目模板
        if (StringUtils.isEmpty(insertBackProjectVo.getGitUrl())){
           String filePath = publicTemplate;
           //是否生成simple框架
           if (insertBackProjectVo.getIsConfirm().equals(IS_CONFIRM1)){
               filePath = projectDao.generateSimpleTemplate(projectDo);
               if (!StringUtils.isEmpty(filePath)){
                   System.out.println(filePath);
               }else {
                   throw new GlobalException(RES_DATA_NOT_EXIST,"自动生成代码框架失败");
               }
           }
           //创建新的gitlab后台项目
            GitlabProjectDo gitlabProjectDo = new GitlabProjectDo();
            try {
                GitlabAPI gitlabAPI = new GitlabAPI("http://172.22.5.242",null,null,null);
                gitlabProjectDo = gitlabAPI.createNewProject(insertBackProjectVo.getProjectName(),insertBackProjectVo.getProjectDesc());
                if (ObjectUtils.isEmpty(gitlabProjectDo)){
                    throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
                }
            }catch (Exception e){
                throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
            }
            //上传后台模板代码到gitlab项目中
            projectDao.uploadBackCodeIntoGitlab(gitlabProjectDo,filePath);
            projectDo.setGitUrl(gitlabProjectDo.getHttpUrl());
        }
        mapper.insertSelective(projectDo);
        //事件添加webhook
        ProjectEvent projectEvent = new ProjectEvent(new Object(),projectCode,"http://www.baidu.com",AUTO_DEPLOY1);
        projectPublisher.publish(projectEvent);
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
    public ResPageDTO<ProjectListVo> queryFrontProjectInfo(QueryProjectListVo queryProjectListVo) {
        Pager<ProjectListVo> pager = new Pager<>();
        pager.setPageNo(queryProjectListVo.getPageNum());
        pager.setLimit(queryProjectListVo.getPageSize());
        Map<String,Object> map = new HashMap<>();
        map.put("start",pager.getStart());
        map.put("limit",pager.getLimit());
        List<ProjectListVo> projectListVoList = mapper.findFrontPorjectList(map);
        pager.setData(projectListVoList);
        return baseSupport.pagerCopy(pager,ProjectListVo.class);
    }

    @Override
    public void addFrontProjectInfo(InsertFrontProjectVo insertFrontProjectVo) {
        //项目基本信息
        ProjectDo projectDo = baseSupport.objectCopy(insertFrontProjectVo,ProjectDo.class);
        //定义项目code(项目名大写)
        String projectCode = insertFrontProjectVo.getProjectName().toUpperCase();
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
            GitlabProjectDo gitlabProjectDo = new GitlabProjectDo();
            try {
                GitlabAPI gitlabAPI = new GitlabAPI("http://172.22.5.242",null,null,null);
                gitlabProjectDo = gitlabAPI.createNewProject(insertFrontProjectVo.getProjectName(),insertFrontProjectVo.getProjectDesc());
                if (ObjectUtils.isEmpty(gitlabProjectDo)){
                    throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
                }
            }catch (Exception e){
                throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
            }
            //是否生成vue框架
            if (IS_CONFIRM0.equals(insertFrontProjectVo.getIsConfirm())){
                projectDao.uploadFrontCodeIntoGitlab(gitlabProjectDo, publicTemplate);
            }else if (IS_CONFIRM1.equals(insertFrontProjectVo.getIsConfirm())){
                //上传后台模板代码到gitlab项目中
                if (insertFrontProjectVo.getProjectType().equals(FRONT_PROJECT_TEMPLATE1)){
                    projectDao.uploadFrontCodeIntoGitlab(gitlabProjectDo, frontTemplate+FRONT_PROJECT_TYPE1);
                }else if (insertFrontProjectVo.getProjectType().equals(FRONT_PROJECT_TEMPLATE2)){
                    projectDao.uploadFrontCodeIntoGitlab(gitlabProjectDo, frontTemplate+FRONT_PROJECT_TYPE2);
                }
            }
            projectDo.setGitUrl(gitlabProjectDo.getHttpUrl());
        }
        mapper.insertSelective(projectDo);
        //事件添加webhook
        ProjectEvent projectEvent = new ProjectEvent(new Object(),projectCode,"http://www.baidu.com",AUTO_DEPLOY1);
        projectPublisher.publish(projectEvent);
    }

    @Override
    public void execDeployBackProject(DeployBackPorjectVo deployBackPorjectVo) {
        ProjectDoExample example = new ProjectDoExample();
        example.createCriteria().andProjectCodeEqualTo(deployBackPorjectVo.getProjectCode());
        List<ProjectDo> projectDoList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(projectDoList)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目信息不存在");
        }
        ProjectDo projectDo = projectDoList.get(0);
        if (!PROJECT_STATUS2.equals(projectDo.getProjectStatus())){
            throw new GlobalException(RES_ILLEGAL_OPERATION,"项目状态不是已创建，不能部署");
        }
        //获取项目运行环境信息
        SimpleDeployDTO dto = mapper.findDeployProjectInfo(deployBackPorjectVo.getProjectCode());
        dto.setBranchName(deployBackPorjectVo.getBranchName());
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
        Map<String,Object> map = new HashMap<>();
        map.put("code",updateProjectStatusVo.getCode());
        map.put("type",updateProjectStatusVo.getType());
        ProjectDo projectDo = mapper.queryProjectInfo(map);
        if (!ObjectUtils.isEmpty(projectDo)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"未找到对应的项目信息");
        }
        if (OBJECT_TYPE1.equals(updateProjectStatusVo.getType())){
            //运行环境
            if (PROJECT_STATUS1.equals(updateProjectStatusVo.getType())){
                projectDo.setProjectStatus(PROJECT_STATUS6.toString());
            }else if (PROJECT_STATUS5.equals(updateProjectStatusVo.getType())){
                projectDo.setProjectStatus(PROJECT_STATUS2.toString());
            }
        }else if (OBJECT_TYPE2.equals(updateProjectStatusVo.getType())){
            //软件环境
            if (PROJECT_STATUS1.equals(updateProjectStatusVo.getType())){
                projectDo.setProjectStatus(PROJECT_STATUS5.toString());
            }else if (PROJECT_STATUS6.equals(updateProjectStatusVo.getType())){
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
        GitlabAPI gitlabAPI = new GitlabAPI("http://172.22.5.242",null,null,null);
        //获取项目gtilab id
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
    public void execDeployFrontProject(DeployFrontPorjectVo deployFrontPorjectVo) {
        ProjectDoExample example = new ProjectDoExample();
        example.createCriteria().andProjectCodeEqualTo(deployFrontPorjectVo.getProjectCode());
        List<ProjectDo> projectDoList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(projectDoList)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"项目信息不存在");
        }
        ProjectDo projectDo = projectDoList.get(0);
        if (!PROJECT_STATUS2.equals(projectDo.getProjectStatus())){
            throw new GlobalException(RES_ILLEGAL_OPERATION,"项目状态不是已创建，不能部署");
        }
        //获取项目运行环境信息
        SimpleDeployDTO dto = mapper.findDeployProjectInfo(deployFrontPorjectVo.getProjectCode());
        VueDeployDTO deployDTO = baseSupport.objectCopy(dto,VueDeployDTO.class);
        deployDTO.setBranchName(deployFrontPorjectVo.getBranchName());
        deployDTO.setServiceUrl(deployFrontPorjectVo.getServiceUrl());
        //调取服务器部署项目脚本
        DeployEvent deployEvent = new DeployEvent(new Object(),null,deployDTO,PROJECT_TYPE1,projectDo.getId().intValue());
        deployPublisher.publish(deployEvent);
        //修改项目状态为部署中
        projectDo.setProjectStatus(PROJECT_STATUS3.toString());
        mapper.updateByPrimaryKeySelective(projectDo);
    }

    @Override
    public void updateProjectDeploy(AutoDeployVo autoDeployVo) {
        ProjectDo projectDo = new ProjectDo();
        projectDo.setId(autoDeployVo.getId().longValue());
        String projectCode = autoDeployVo.getProjectCode();
        if (AUTO_DEPLOY0.equals(autoDeployVo.getAutoDeploy())){
            //开启自动部署
            projectDo.setAutoDeploy(AUTO_DEPLOY1);
            //事件添加webhook
            ProjectEvent projectEvent = new ProjectEvent(new Object(),projectCode,"http://www.baidu.com",AUTO_DEPLOY1);
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


}
