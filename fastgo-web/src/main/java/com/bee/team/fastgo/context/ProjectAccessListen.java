package com.bee.team.fastgo.context;

import com.bee.team.fastgo.mapper.GitlabUserDoMapperExt;
import com.bee.team.fastgo.mapper.ProjectDoMapperExt;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.project.gitlab.GitlabAPI;
import com.bee.team.fastgo.project.model.GitlabAccess;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.spring.simple.development.support.exception.GlobalException;
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

import static com.bee.team.fastgo.constant.ProjectConstant.PROJECT_ACCESS_TYPE1;
import static com.bee.team.fastgo.constant.ProjectConstant.PROJECT_ACCESS_TYPE2;
import static com.spring.simple.development.support.exception.ResponseCode.*;

/**
 * @author hs
 * @date 2020/8/4 13:40
 * @desc 项目权限分配用户监听器
 **/

@Component
@EnableAsync
public class ProjectAccessListen {

    private static final Logger logger = LogManager.getLogger(ProjectAccessListen.class);

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.admin.privateToken}")
    private String privateToken;

    @Autowired
    private ProjectDoMapperExt projectDoMapperExt;

    @Autowired
    private GitlabUserDoMapperExt gitlabUserDoMapperExt;

    @Async
    @EventListener(ProjectAccessEvent.class)
    public void listener(ProjectAccessEvent projectAccessEvent) {
        //操作类型
        Integer type = projectAccessEvent.getType();
        //用户id
        List<Integer> userIds = projectAccessEvent.getUserIds();
        GitlabUserDoExample userDoExample = new GitlabUserDoExample();
        userDoExample.createCriteria().andUserIdIn(userIds);
        List<GitlabUserDo> gitlabUsers = gitlabUserDoMapperExt.selectByExample(userDoExample);
        List<Integer> gitlabUserIds = gitlabUsers.stream().map(GitlabUserDo::getGitlabUserId).collect(Collectors.toList());
        //项目信息
        Integer projectId = projectAccessEvent.getProjectId();
        ProjectDo projectDo = projectDoMapperExt.selectByPrimaryKey(projectId.longValue());
        //获取gitlab api接口
        GitlabAPI gitlabAPI = new GitlabAPI(gitlabUrl,privateToken);
        GitlabProjectDo gitlabProjectDo = null;
        try {
            List<GitlabProjectDo> gitlabProjectDos = gitlabAPI.getAllProject();
            List<GitlabProjectDo> gitlabProjectDoList = gitlabProjectDos.stream().filter(r -> r.getName().equals(projectDo.getProjectName())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(gitlabProjectDoList)){
                throw new GlobalException(RES_DATA_NOT_EXIST,"gitlab中不存在该项目");
            }
            gitlabProjectDo = gitlabProjectDoList.get(0);
        }catch (Exception e){
            logger.error(">>>>>>查询gitlab中所有项目失败");
        }
        if (ObjectUtils.isEmpty(gitlabProjectDo)){
            throw new GlobalException(RES_DATA_NOT_EXIST,"gitlab项目不能为空");
        }
        if (type.equals(PROJECT_ACCESS_TYPE1)){
            try{
                //项目添加成员
                for (Integer id : gitlabUserIds){
                    GitlabAccess gitlabAccess = gitlabAPI.addProjectMember(gitlabProjectDo.getId(), id);
                    if (ObjectUtils.isEmpty(gitlabAccess)){
                        throw new GlobalException(RES_DATA_NOT_EXIST,"项目访问权限新增失败");
                    }
                }
            }catch (Exception e){
                throw new GlobalException(RES_ILLEGAL_OPERATION,"项目访问权限新增失败");
            }

        }else if (type.equals(PROJECT_ACCESS_TYPE2)){
            //项目移除成员
            try{
                for (Integer id : gitlabUserIds){
                    GitlabAccess gitlabAccess = gitlabAPI.delProjectMember(gitlabProjectDo.getId(),id);
                    if (!ObjectUtils.isEmpty(gitlabAccess)){
                        throw new GlobalException(RES_DATA_EXIST,"项目访问权限移除失败");
                    }
                }
            }catch (Exception e){
                throw new GlobalException(RES_ILLEGAL_OPERATION,"项目访问权限移除失败");
            }
        }

    }

}
