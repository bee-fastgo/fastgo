package com.bee.team.fastgo.dao.impl;

import com.bee.team.fastgo.constant.ProjectConstant;
import com.bee.team.fastgo.dao.ProjectDao;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.simple.code.generate.component.ComponentNameGenerate;
import com.simple.code.generate.dto.SimpleConfigDto;
import com.simple.code.generate.simpleenum.ComponentEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectDaoImpl implements ProjectDao {

    @Value(value = "gitlab.username")
    String username;

    @Value(value = "gitlab.password")
    String password;

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
    public void uploadCodeIntoGitlab(GitlabProjectDo gitlabProjectDo, String filePath) {
        int reg = filePath.indexOf(gitlabProjectDo.getName());
        String path = filePath.substring(0,reg);
        try {
            //拉取gitlab代码到本地
            String pullCode = "cd " + path + " && mkdir tempmkdir && cd tempmkdir && git clone http://"+username+":"+password+"@"+gitlabProjectDo.getHttpUrl().substring(7);
            Runtime r = Runtime.getRuntime();
            r.exec(pullCode).waitFor();
            //拷贝本地模板代码到git项目中
            String mvCode = "mv " + filePath + "/* " +path+"/tempmkdir/"+gitlabProjectDo.getName();
            r.exec(mvCode).waitFor();
            //push本地代码到gitlab中
            String pushCode = "cd "+path+"/tempmkdir/"+gitlabProjectDo.getName()+" && git add * && git commit -m 'first commit code' && git push origin master";
            r.exec(pushCode).waitFor();
            //删除本地文件
            String deleteCode = "rm "+path+" -rf";
            r.exec(deleteCode).waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
