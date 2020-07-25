package com.bee.team.fastgo.service.project.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.config.service.ConfigProjectBo;
import com.bee.team.fastgo.constant.ProjectConstant;
import com.bee.team.fastgo.context.ProjectEvent;
import com.bee.team.fastgo.context.ProjectPublisher;
import com.bee.team.fastgo.dao.ProjectDao;
import com.bee.team.fastgo.mapper.*;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.project.Gitlab.GitlabAPI;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.bee.team.fastgo.service.project.ProjectBo;
import com.bee.team.fastgo.utils.StringUtil;
import com.bee.team.fastgo.vo.project.*;
import com.bee.team.fastgo.vo.project.req.*;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.utils.Pager;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bee.team.fastgo.constant.ProjectConstant.*;
import static com.spring.simple.development.support.exception.ResponseCode.*;


@Service
public class ProjectBoImpl extends AbstractLavaBoImpl<ProjectDo, ProjectDoMapperExt, ProjectDoExample> implements ProjectBo {

    @Autowired
    public void setBaseMapper(ProjectDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectPublisher projectPublisher;

    @Autowired
    private ProjectProfileDoMapperExt projectProfileDoMapperExt;

    @Autowired
    private ProfileConfigRelationDoMapperExt profileConfigRelationDoMapperExt;

    @Autowired
    private ProfileSoftwareRelationDoMapperExt profileSoftwareRelationDoMapperExt;

    @Autowired
    private ProfileRunprofileRelationDoMapperExt profileRunprofileRelationDoMapperExt;

    @Autowired
    private GitlabAPI gitlabAPI;

    @Autowired
    private ConfigProjectBo configProjectBo;

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
        Integer flag = addBackProjectProfile(insertBackProjectProfileVo);
        projectDo.setProjectStatus(flag.toString());

        //生成后台项目模板
        if (StringUtils.isEmpty(insertBackProjectVo.getGitUrl())){
           String filePath = projectDao.generateSimpleTemplate(projectDo);
           if (!StringUtils.isEmpty(filePath)){
               System.out.println(filePath);
           }else {
               throw new GlobalException(RES_DATA_NOT_EXIST,"自动生成代码框架失败");
           }
           //创建新的gitlab后台项目
            GitlabProjectDo gitlabProjectDo = new GitlabProjectDo();
            try {
                gitlabProjectDo = gitlabAPI.createNewProject(insertBackProjectVo.getProjectName(),insertBackProjectVo.getProjectDesc());
                if (ObjectUtils.isEmpty(gitlabProjectDo)){
                    throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
                }
            }catch (Exception e){
                throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
            }
            //上传后台模板代码到gitlab项目中
            projectDao.uploadCodeIntoGitlab(gitlabProjectDo,filePath);
            projectDo.setGitUrl(gitlabProjectDo.getHttpUrl());
        }
        mapper.insertSelective(projectDo);
        //事件添加webhook
        ProjectEvent projectEvent = new ProjectEvent(new Object(),projectCode,"http://www.baidu.com");
        projectPublisher.publish(projectEvent);
    }

    @Override
    public Integer addBackProjectProfile(InsertBackProjectProfileVo insertBackProjectProfileVo) {
        //项目信息
        //flag 新增环境时，项目的状态
        int flag = 1;
        Map<String,Object> map = new HashMap<>();
        //1.项目，项目环境关联信息
        ProjectProfileDo projectProfileDo = baseSupport.objectCopy(insertBackProjectProfileVo,ProjectProfileDo.class);
        //定义项目环境code（项目名_PROFILE）
        String profileName = insertBackProjectProfileVo.getProfileName();
        String projectProfileCode = profileName.toUpperCase() + "_" + "profile".toUpperCase();
        projectProfileDo.setProjectCode(insertBackProjectProfileVo.getProjectCode());
        projectProfileDo.setPrifileCode(projectProfileCode);
        projectProfileDo.setBranchName(insertBackProjectProfileVo.getBranchName());
        if (StringUtils.isEmpty(insertBackProjectProfileVo.getBranchName())){
            projectProfileDo.setBranchName(ProjectConstant.PROJECT_BRANCH);
        }
        projectProfileDoMapperExt.insertSelective(projectProfileDo);

        //2.项目环境，运行环境关联信息
        ProfileRunprofileRelationDo pDo = baseSupport.objectCopy(insertBackProjectProfileVo,ProfileRunprofileRelationDo.class);
        pDo.setPrifileCode(projectProfileCode);
        //运行环境已存在时
        if (StringUtils.isEmpty(insertBackProjectProfileVo.getRunProfileCode())){
            //定义运行环境code
            String runProfileCode = profileName.toUpperCase() + "_" + "runprofile".toUpperCase();
            pDo.setRunProfileCode(runProfileCode);
            // TODO: 2020/7/22 调取服务器接口，创建新的运行环境,获取运行环境元配置,修改flag
            Map<String,Object> runProfileConfig = new HashMap<>();
            pDo.setRunProfileConfig(runProfileConfig.toString());
        }
        profileRunprofileRelationDoMapperExt.insertSelective(pDo);
        //添加元配置到项目信息中
        Map<String,Object> base = StringUtil.strToMap(pDo.getRunProfileConfig());
        if (base == null){
            base = new HashMap<>();
        }
        base.put("name",insertBackProjectProfileVo.getProjectName());
        base.put("description",insertBackProjectProfileVo.getProjectDesc());
        map.put("base",base);

        //3.项目环境，软件环境关联信息
        List<SoftwareInfoVo> softwareInfoVoList = insertBackProjectProfileVo.getSoftwareInfoVos();
        if (CollectionUtils.isEmpty(softwareInfoVoList)){
            throw new GlobalException(RES_PARAM_IS_EMPTY,"软件环境不能为空");
        }
        List<ProfileSoftwareRelationDo> dos = new ArrayList<>();
        for (SoftwareInfoVo softwareInfoVo : softwareInfoVoList){
            ProfileSoftwareRelationDo psDo = baseSupport.objectCopy(softwareInfoVo,ProfileSoftwareRelationDo.class);
            psDo.setPrifileCode(projectProfileCode);
            psDo.setRunServerIp(softwareInfoVo.getSoftwareServerIp());
            //如果软件环境不存在
            if (StringUtils.isEmpty(softwareInfoVo.getSoftwareCode())){
                String softwareName = softwareInfoVo.getSoftwareName();
                //自定义软件环境code
                String softwareCode = profileName.toUpperCase() + "_" + softwareName.toUpperCase();
                psDo.setSoftwareCode(softwareCode);
                // TODO: 2020/7/22 调取服务器接口，创建新的软件环境，获取软件环境元配置,修改flag
                Map<String,Object> softwareConfig = new HashMap<>();
                psDo.setSoftwareConfig(softwareConfig.toString());
            }
            dos.add(psDo);
            //添加元配置到项目信息中
            map.put(softwareInfoVo.getSoftwareName(),StringUtil.strToMap(psDo.getSoftwareConfig()));
        }
        //批量新增项目环境，软件环境关联表
        profileSoftwareRelationDoMapperExt.batchInsertProfileSoftware(dos);

        //4.项目环境，配置中心关联信息
        // TODO: 2020/7/22 获取配置中心信息
        String configCode = configProjectBo.insertProject(map);
        ProfileConfigRelationDo pcDo = new ProfileConfigRelationDo();
        pcDo.setConfigCode(configCode);
        pcDo.setPrifileCode(projectProfileCode);
        profileConfigRelationDoMapperExt.insertSelective(pcDo);
        return flag;
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
    public void addFrontProjectInfo(InsertBackProjectVo insertBackProjectVo) {
        //项目基本信息
        ProjectDo projectDo = baseSupport.objectCopy(insertBackProjectVo,ProjectDo.class);
        //定义项目code(项目名大写)
        String projectCode = insertBackProjectVo.getProjectName().toUpperCase();
        projectDo.setProjectCode(projectCode);
        projectDo.setProjectType(ProjectConstant.PROJECT_TYPE2.toString());
        projectDo.setProjectStatus(PROJECT_STATUS1.toString());

        //配置环境
        InsertBackProjectProfileVo insertBackProjectProfileVo = baseSupport.objectCopy(insertBackProjectVo,InsertBackProjectProfileVo.class);
        addBackProjectProfile(insertBackProjectProfileVo);

        //生成前台项目模板
        if (StringUtils.isEmpty(insertBackProjectVo.getGitUrl())){
            //创建新的gitlab后台项目
            GitlabProjectDo gitlabProjectDo = new GitlabProjectDo();
            try {
                gitlabProjectDo = gitlabAPI.createNewProject(insertBackProjectVo.getProjectName(),insertBackProjectVo.getProjectDesc());
                if (ObjectUtils.isEmpty(gitlabProjectDo)){
                    throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
                }
            }catch (Exception e){
                throw new GlobalException(RES_ILLEGAL_OPERATION,"gitlab项目创建失败");
            }
            //上传后台模板代码到gitlab项目中
            projectDao.uploadCodeIntoGitlab(gitlabProjectDo,"");
            projectDo.setGitUrl(gitlabProjectDo.getHttpUrl());
        }
    }

    @Override
    public String execDeployBackProject(DeployBackPorjectVo deployBackPorjectVo) {
        // TODO: 2020/7/24 调取服务器部署项目脚本
        return "";
    }

    /**
     * @param updateProjectStatusVo
     * @return
     * @author hs
     * @date 2020/7/25
     * @desc TODO
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


}
