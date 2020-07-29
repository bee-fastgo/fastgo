package com.bee.team.fastgo.context;

import com.bee.team.fastgo.mapper.ProjectDoMapperExt;
import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.model.ProjectDoExample;
import com.bee.team.fastgo.project.gitlab.GitlabAPI;
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

import static com.bee.team.fastgo.constant.ProjectConstant.AUTO_DEPLOY0;
import static com.bee.team.fastgo.constant.ProjectConstant.AUTO_DEPLOY1;

@Component
@EnableAsync
public class ProjectListen {

    private static final Logger logger = LogManager.getLogger(ProjectListen.class);

    @Autowired
    private ProjectDoMapperExt projectDoMapperExt;

    @Async
    @EventListener(ProjectEvent.class)
    public void listener(ProjectEvent projectEvent){
        if (AUTO_DEPLOY1.equals(projectEvent.getType())){
            String projectCode = projectEvent.getProjectCode();
            String url = projectEvent.getUrl();
            System.out.println(">>>>>>>>>>>>>>>>>>"+projectCode+">>>>>>>>>>"+url);
            GitlabAPI gitlabAPI = new GitlabAPI("http://172.22.5.242",null,null,null);
            try{
                ProjectDoExample example = new ProjectDoExample();
                example.createCriteria().andProjectCodeEqualTo(projectCode);
                List<ProjectDo> projectList = projectDoMapperExt.selectByExample(example);
                if (!CollectionUtils.isEmpty(projectList)){
                    ProjectDo projectDo = projectList.get(0);
                    List<GitlabProjectDo> gitlibProjects = gitlabAPI.getAllProject();
                    List<GitlabProjectDo> gitlibProjectList= gitlibProjects.stream().filter(r -> r.getName().equals(projectDo.getProjectName())).collect(Collectors.toList());
                    GitlabProjectDo gitlabProjectDo = gitlibProjectList.get(0);
                    GitlabProjectHook gitlabProjectHook = gitlabAPI.addWebhook(gitlabProjectDo.getId(),url);
                    if (ObjectUtils.isEmpty(gitlabProjectHook)){
                        logger.error("项目webhook添加失败");
                    }
                }
            }catch (Exception e){
                logger.error("项目webhook添加失败："+e.getMessage());
            }
        }else if (AUTO_DEPLOY0.equals(projectEvent.getType())){
            String projectCode = projectEvent.getProjectCode();
            GitlabAPI gitlabAPI = new GitlabAPI("http://172.22.5.242",null,null,null);
            try{
                ProjectDoExample example = new ProjectDoExample();
                example.createCriteria().andProjectCodeEqualTo(projectCode);
                List<ProjectDo> projectList = projectDoMapperExt.selectByExample(example);
                if (!CollectionUtils.isEmpty(projectList)){
                    ProjectDo projectDo = projectList.get(0);
                    List<GitlabProjectDo> gitlibProjects = gitlabAPI.getAllProject();
                    List<GitlabProjectDo> gitlibProjectList= gitlibProjects.stream().filter(r -> r.getName().equals(projectDo.getProjectName())).collect(Collectors.toList());
                    GitlabProjectDo gitlabProjectDo = gitlibProjectList.get(0);
                    List<GitlabProjectHook> gitlabProjectHooks = gitlabAPI.getAllWebhook(gitlabProjectDo.getId());
                    List<GitlabProjectHook> gitlabProjectHookList = gitlabProjectHooks.stream().filter(g -> g.isTagPushEvents()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(gitlabProjectHookList)){
                        GitlabProjectHook gitlabProjectHook = gitlabProjectHookList.get(0);
                        GitlabProjectHook hook = gitlabAPI.deleteWebhook(gitlabProjectDo.getId(),gitlabProjectHook.getId());
                        if (!ObjectUtils.isEmpty(hook)){
                            logger.error("项目webhook删除失败");
                        }
                    }
                }
            }catch (Exception e){
                logger.error("项目webhook删除失败："+e.getMessage());
            }
        }
    }


}
