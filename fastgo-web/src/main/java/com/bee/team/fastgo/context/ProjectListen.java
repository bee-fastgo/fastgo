package com.bee.team.fastgo.context;

import com.bee.team.fastgo.mapper.ProjectDoMapperExt;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.model.ProjectDoExample;
import com.bee.team.fastgo.project.Gitlab.GitlabAPI;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.bee.team.fastgo.project.model.GitlabProjectHook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableAsync
public class ProjectListen {

    private static final Logger logger = LogManager.getLogger(ProjectListen.class);

    @Autowired
    private ProjectDoMapperExt projectDoMapperExt;

    @Async
    @EventListener(ProjectEvent.class)
    public void listener(ProjectEvent projectEvent){
        String projectCode = projectEvent.getProjectCode();
        String url = projectEvent.getUrl();
        GitlabAPI gitlabAPI = new GitlabAPI("http://172.22.5.242",null,null,null);
        try{
            ProjectDoExample example = new ProjectDoExample();
            example.createCriteria().andProjectCodeEqualTo(projectCode);
            List<ProjectDo> projectList = projectDoMapperExt.selectByExample(example);
            if (!CollectionUtils.isEmpty(projectList)){
                ProjectDo projectDo = projectList.get(0);
                List<GitlabProjectDo> gitlibProjects = gitlabAPI.getAllProject();
                List<GitlabProjectDo> gitlibProjectList= gitlibProjects.stream().filter(r -> r.getName() == projectDo.getProjectName()).collect(Collectors.toList());
                GitlabProjectDo gitlabProjectDo = gitlibProjectList.get(0);
                GitlabProjectHook gitlabProjectHook = gitlabAPI.addWebhook(gitlabProjectDo.getId(),url);
                if (ObjectUtils.isEmpty(gitlabProjectHook)){
                    logger.error("项目webhook添加失败");
                }
            }
        }catch (Exception e){
            logger.error("项目webhook添加失败："+e.getMessage());
        }
    }


}
