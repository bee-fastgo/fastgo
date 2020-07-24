package com.bee.team.fastgo.dao.impl;

import com.bee.team.fastgo.constant.ProjectConstant;
import com.bee.team.fastgo.dao.ProjectDao;
import com.bee.team.fastgo.model.ProjectDo;
import com.simple.code.generate.component.ComponentNameGenerate;
import com.simple.code.generate.dto.SimpleConfigDto;
import com.simple.code.generate.simpleenum.ComponentEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectDaoImpl implements ProjectDao {


    @Override
    public String generateSimpleTemplate(ProjectDo projectDo) {
        SimpleConfigDto simpleConfigDto = new SimpleConfigDto();
        simpleConfigDto.setBranchName(ProjectConstant.PROJECT_BRANCH);
        simpleConfigDto.setProjectCode(projectDo.getProjectCode());
        simpleConfigDto.setFastGoServer("http://tech20.com");
        // mybatis自动生成代码
        simpleConfigDto.setMybatisIsAutoGenerate(true);
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
}
