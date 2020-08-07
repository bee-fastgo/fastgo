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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.normal.privateToken}")
    private String privateToken;

    @Autowired
    private ProjectDoMapperExt projectDoMapperExt;

    @Async
    @EventListener(ProjectEvent.class)
    public void listener(ProjectEvent projectEvent){
        //开启项目自动部署
        if (AUTO_DEPLOY1.equals(projectEvent.getType())){
            String projectCode = projectEvent.getProjectCode();
            String url = projectEvent.getUrl();
            System.out.println(">>>>>>>>>>>>>>>>>>"+projectCode+">>>>>>>>>>"+url);
            GitlabAPI gitlabAPI = new GitlabAPI(gitlabUrl,privateToken);
            try{
                ProjectDoExample example = new ProjectDoExample();
                example.createCriteria().andProjectCodeEqualTo(projectCode);
                List<ProjectDo> projectList = projectDoMapperExt.selectByExample(example);
                if (!CollectionUtils.isEmpty(projectList)){
                    ProjectDo projectDo = projectList.get(0);
                    //获取gitlab上所有项目信息，并过滤出当前项目
                    List<GitlabProjectDo> gitlibProjects = gitlabAPI.getAllProject();
                    List<GitlabProjectDo> gitlibProjectList= gitlibProjects.stream().filter(r -> r.getName().equals(projectDo.getProjectName())).collect(Collectors.toList());
                    GitlabProjectDo gitlabProjectDo = gitlibProjectList.get(0);
                    //调用gitlab接口，添加项目push事件钩子
                    GitlabProjectHook gitlabProjectHook = gitlabAPI.addWebhook(gitlabProjectDo.getId(),url);
                    if (ObjectUtils.isEmpty(gitlabProjectHook)){
                        logger.error("项目webhook添加失败");
                    }
                }
            }catch (Exception e){
                logger.error("项目webhook添加失败："+e.getMessage());
            }
        //关闭项目自动部署
        }else if (AUTO_DEPLOY0.equals(projectEvent.getType())){
            String projectCode = projectEvent.getProjectCode();
            GitlabAPI gitlabAPI = new GitlabAPI(gitlabUrl,privateToken);
            try{
                ProjectDoExample example = new ProjectDoExample();
                example.createCriteria().andProjectCodeEqualTo(projectCode);
                List<ProjectDo> projectList = projectDoMapperExt.selectByExample(example);
                if (!CollectionUtils.isEmpty(projectList)){
                    ProjectDo projectDo = projectList.get(0);
                    //获取gitlab上所有项目信息，并过滤出当前项目
                    List<GitlabProjectDo> gitlibProjects = gitlabAPI.getAllProject();
                    List<GitlabProjectDo> gitlibProjectList= gitlibProjects.stream().filter(r -> r.getName().equals(projectDo.getProjectName())).collect(Collectors.toList());
                    GitlabProjectDo gitlabProjectDo = gitlibProjectList.get(0);
                    //获取当前项目的所有钩子，并过滤出当前部署钩子，并移除
                    List<GitlabProjectHook> gitlabProjectHooks = gitlabAPI.getAllWebhook(gitlabProjectDo.getId());
                    List<GitlabProjectHook> gitlabProjectHookList = gitlabProjectHooks.stream().filter(g -> g.isPushEvents()).collect(Collectors.toList());
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
