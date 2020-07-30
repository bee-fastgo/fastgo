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
import com.bee.team.fastgo.service.api.server.SoftwareProfileApi;
import com.bee.team.fastgo.service.api.server.dto.req.ReqCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.dto.res.ResCreateSoftwareDTO;
import com.bee.team.fastgo.service.config.ProjectConfigBo;
import com.bee.team.fastgo.service.server.ServerRunProfileBo;
import com.bee.team.fastgo.utils.StringUtil;
import com.bee.team.fastgo.vo.project.req.InsertBackProjectProfileVo;
import com.bee.team.fastgo.vo.project.req.SoftwareInfoVo;
import com.bee.team.fastgo.vo.server.AddServerRunProfileVo;
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

import static com.bee.team.fastgo.constant.ProjectConstant.*;
import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

@Component
public class ProjectDaoImpl implements ProjectDao {

    @Value("${gitlab.username}")
    private String username;

    @Value("${gitlab.password}")
    private String password;

    @Value("${projectUrl}")
    private String projectUrl;

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ProjectConfigBo projectConfigBo;

    @Autowired
    private SoftwareProfileApi softwareProfileApi;

    @Autowired
    private ServerRunProfileBo serverRunProfileBo;

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
        simpleConfigDto.setFastGoServer("http://172.22.5.248:9999/config/getProjectConfigByCode");
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
//        componentEnums.add(ComponentEnum.generator);
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
            Git git = GitUtil.fromCloneRepository(gitlabProjectDo.getHttpUrl(),projectUrl+"/tempbackdir/"+gitlabProjectDo.getName(),provider);
            //添加代码到本地库
            String mv = "cp -a " + filePath + "/. " + projectUrl+"/tempbackdir/"+gitlabProjectDo.getName()+"/";
            String[] cmd = new String[]{"sh","-c",mv};
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(cmd).waitFor();
            //commit提交代码
            GitUtil.commit(git,"第一次提交",provider);
            //默认push到master分支
            GitUtil.push(git,"master",provider);
            //删除本地的临时项目
            String rm = "rm -rf " + projectUrl+"/tempbackdir/*";
            String[] rmd = new String[]{"sh","-c",rm};
            runtime.exec(rmd).waitFor();
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
            Git git = GitUtil.fromCloneRepository(gitlabProjectDo.getHttpUrl(),projectUrl+"/tempfrontdir/"+gitlabProjectDo.getName(),provider);
            //添加代码到本地库
            String mv = "cp -a " + filePath + "/. " + projectUrl+"/tempfrontdir/"+gitlabProjectDo.getName()+"/";
            String[] cmd = new String[]{"sh","-c",mv};
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(cmd).waitFor();
            //commit提交代码
            GitUtil.commit(git,"第一次提交",provider);
            //默认push到master分支
            GitUtil.push(git,"master",provider);
            //删除本地的临时项目
            String rm = "rm -rf " + projectUrl+"/tempfrontdir/*";
            String[] rmd = new String[]{"sh","-c",rm};
            runtime.exec(rmd).waitFor();
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
        projectProfileDo.setProfileCode(projectProfileCode);
        if (StringUtils.isEmpty(projectProfileDo.getBranchName())){
            projectProfileDo.setBranchName(ProjectConstant.PROJECT_BRANCH);
        }
        projectProfileDoMapperExt.insertSelective(projectProfileDo);

        //2.项目环境，运行环境关联信息
        ProfileRunprofileRelationDo pDo = baseSupport.objectCopy(insertBackProjectProfileVo,ProfileRunprofileRelationDo.class);
        pDo.setProfileCode(projectProfileCode);
        //调取服务器接口，创建新的运行环境,获取运行环境元配置,修改flag
        AddServerRunProfileVo vo = new AddServerRunProfileVo();
        vo.setServerIp(insertBackProjectProfileVo.getRunServerIp());
        vo.setSoftwareName(insertBackProjectProfileVo.getProjectName());
        ServerRunProfileDo serverRunProfileDo = serverRunProfileBo.addServerRunProfileDo(vo);
        pDo.setRunProfileCode(serverRunProfileDo.getRunProfileCode());
        pDo.setRunProfileConfig(serverRunProfileDo.getSoftwareConfig());
        profileRunprofileRelationDoMapperExt.insertSelective(pDo);
        flag=PROJECT_STATUS6;
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

            //获取软件元配置信息
            ReqCreateSoftwareDTO dto = new ReqCreateSoftwareDTO();
            dto.setIp(psDo.getRunServerIp());
            dto.setSoftwareName(softwareInfoVo.getSoftwareName());
            dto.setVersion(softwareInfoVo.getVersion());
            dto.setProfileCode(projectProfileCode);
            ResCreateSoftwareDTO softwareDTO = softwareProfileApi.createSoftwareEnvironment(dto);
            psDo.setSoftwareCode(softwareDTO.getSoftwareCode());
            psDo.setSoftwareConfig(softwareDTO.getSoftwareConfig());
            //修改项目状态
            if (HAS_PROFILE1.toString().equals(softwareDTO.getConfigFlag())){
                if (PROJECT_STATUS6.equals(flag)){
                    flag = PROJECT_STATUS2;
                }else if (PROJECT_STATUS1.equals(flag)){
                    flag = PROJECT_STATUS5;
                }
            }
            dos.add(psDo);
            //添加元配置到项目信息中
            map.put(softwareInfoVo.getSoftwareName(),StringUtil.strToMap(psDo.getSoftwareConfig()));
        }
        //批量新增项目环境，软件环境关联表
        profileSoftwareRelationDoMapperExt.batchInsertProfileSoftware(dos);

        //4.项目环境，配置中心关联信息
        String configCode = configProjectBo.insertProject(map);
        ProfileConfigRelationDo pcDo = new ProfileConfigRelationDo();
        pcDo.setConfigCode(configCode);
        pcDo.setProfileCode(projectProfileCode);
        profileConfigRelationDoMapperExt.insertSelective(pcDo);
        return flag;
    }
}
