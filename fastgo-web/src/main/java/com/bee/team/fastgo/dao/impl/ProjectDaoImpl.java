package com.bee.team.fastgo.dao.impl;

import com.bee.team.fastgo.constant.ProjectConstant;
import com.bee.team.fastgo.dao.ProjectDao;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.bee.team.fastgo.project.utils.GitUtil;
import com.simple.code.generate.component.ComponentNameGenerate;
import com.simple.code.generate.dto.SimpleConfigDto;
import com.simple.code.generate.simpleenum.ComponentEnum;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectDaoImpl implements ProjectDao {

    @Value(value = "gitlab.username")
    private String username;

    @Value(value = "gitlab.password")
    private String password;

    @Value("projectUrl")
    private String projectUrl;

    @Value("frontTemplate")
    private String frontTemplate;

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

}
