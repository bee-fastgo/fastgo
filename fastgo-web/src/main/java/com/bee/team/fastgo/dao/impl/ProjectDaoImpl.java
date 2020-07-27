package com.bee.team.fastgo.dao.impl;

import com.bee.team.fastgo.config.service.ConfigProjectBo;
import com.bee.team.fastgo.constant.ProjectConstant;
import com.bee.team.fastgo.dao.ProjectDao;
import com.bee.team.fastgo.mapper.ProfileConfigRelationDoMapperExt;
import com.bee.team.fastgo.mapper.ProfileRunprofileRelationDoMapperExt;
import com.bee.team.fastgo.mapper.ProfileSoftwareRelationDoMapperExt;
import com.bee.team.fastgo.mapper.ProjectProfileDoMapperExt;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.bee.team.fastgo.project.utils.GitUtil;
import com.bee.team.fastgo.utils.StringUtil;
import com.bee.team.fastgo.vo.project.req.InsertBackProjectProfileVo;
import com.bee.team.fastgo.vo.project.req.SoftwareInfoVo;
import com.simple.code.generate.component.ComponentNameGenerate;
import com.simple.code.generate.dto.SimpleConfigDto;
import com.simple.code.generate.simpleenum.ComponentEnum;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

@Component
public class ProjectDaoImpl implements ProjectDao {

    @Value("${gitlab.username}")
    private String username;

    @Value("${gitlab.password}")
    private String password;

    @Value("${projectUrl}")
    private String projectUrl;

    @Value("${frontTemplate}")
    private String frontTemplate;

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ProjectProfileDoMapperExt projectProfileDoMapperExt;

    @Autowired
    private ProfileConfigRelationDoMapperExt profileConfigRelationDoMapperExt;

    @Autowired
    private ProfileSoftwareRelationDoMapperExt profileSoftwareRelationDoMapperExt;

    @Autowired
    private ProfileRunprofileRelationDoMapperExt profileRunprofileRelationDoMapperExt;

    @Autowired
    private ConfigProjectBo configProjectBo;

    @Override
    public String generateSimpleTemplate(ProjectDo projectDo) {
        SimpleConfigDto simpleConfigDto = new SimpleConfigDto();
        simpleConfigDto.setBranchName(ProjectConstant.PROJECT_BRANCH);
        simpleConfigDto.setProjectCode(projectDo.getProjectCode());
        simpleConfigDto.setFastGoServer("http://tech20.com");
        // mybatis自动生成代码
        simpleConfigDto.setMybatisIsAutoGenerate(false);
        simpleConfigDto.setMysqlIp("172.22.5.248");
        simpleConfigDto.setMysqlPort("3306");
        simpleConfigDto.setDataBaseName("insurance");
        simpleConfigDto.setMysqlUser("root");
        simpleConfigDto.setMysqlPassword("123456");

        List<ComponentEnum> componentEnums = new ArrayList<>();
        componentEnums.add(ComponentEnum.springMvc);
        componentEnums.add(ComponentEnum.mybatis);
//        componentEnums.add(ComponentEnum.dubbo);
//        componentEnums.add(ComponentEnum.cassandra);
//        componentEnums.add(ComponentEnum.kafka);
//        componentEnums.add(ComponentEnum.mongodb);
//        componentEnums.add(ComponentEnum.job);
//        componentEnums.add(ComponentEnum.es);
//        componentEnums.add(ComponentEnum.shiroCas);
        componentEnums.add(ComponentEnum.swagger);
//        componentEnums.add(ComponentEnum.alert);
        String projectPath = null;
        try {
            projectPath = new ComponentNameGenerate(projectDo.getProjectName(), projectDo.getPackageName(), simpleConfigDto, componentEnums).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectPath;
    }

    @Override
    public void uploadBackCodeIntoGitlab(GitlabProjectDo gitlabProjectDo, String filePath) {
        //获取gitlab管理凭证
        CredentialsProvider provider = GitUtil.createCredential(username,password);
        try {
            //克隆远程分支到本地
            Git git = GitUtil.fromCloneRepository(gitlabProjectDo.getHttpUrl(),projectUrl+"/tempbackdir",provider);
            //添加代码到本地库
            String mv = "mv " + filePath + "/* " + projectUrl+"/tempbackdir";
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(mv).waitFor();
            //commit提交代码
            GitUtil.commit(git,"第一次提交",provider);
            //默认push到master分支
            GitUtil.push(git,"master",provider);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void uploadFrontCodeIntoGitlab(GitlabProjectDo gitlabProjectDo,String filePath) {
        //获取gitlab管理凭证
        CredentialsProvider provider = GitUtil.createCredential(username,password);
        try {
            //克隆远程分支到本地
            Git git = GitUtil.fromCloneRepository(gitlabProjectDo.getHttpUrl(),projectUrl+"/tempfrontdir",provider);
            //添加代码到本地库
            String mv = "cp -r " + frontTemplate + filePath + "/* " + projectUrl+"/tempfrontdir/";
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(mv).waitFor();
            //commit提交代码
            GitUtil.commit(git,"第一次提交",provider);
            //默认push到master分支
            GitUtil.push(git,"master",provider);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Integer addProjectProfile(InsertBackProjectProfileVo insertBackProjectProfileVo) {
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

}
