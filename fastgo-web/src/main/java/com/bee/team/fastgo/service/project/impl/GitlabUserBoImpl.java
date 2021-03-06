package com.bee.team.fastgo.service.project.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.context.ProjectAccessEvent;
import com.bee.team.fastgo.context.ProjectPublisher;
import com.bee.team.fastgo.mapper.GitlabUserDoMapperExt;
import com.bee.team.fastgo.model.GitlabUserDo;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.project.gitlab.GitlabAPI;
import com.bee.team.fastgo.project.model.GitlabUser;
import com.bee.team.fastgo.service.project.GitlabUserBo;
import com.bee.team.fastgo.utils.EmailUtil;
import com.bee.team.fastgo.vo.project.GitlabUserInfoResVo;
import com.bee.team.fastgo.vo.project.UserInfoResVo;
import com.bee.team.fastgo.vo.project.req.DistributionGitlabUserVo;
import com.bee.team.fastgo.vo.project.req.GitlabUserGetProjectVo;
import com.bee.team.fastgo.vo.project.req.GitlabUserInfoVo;
import com.bee.team.fastgo.vo.project.req.ProjectAddMemberVo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.bee.team.fastgo.constant.ProjectConstant.*;
import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;
import static com.spring.simple.development.support.exception.ResponseCode.RES_ILLEGAL_OPERATION;

@Service
public class GitlabUserBoImpl extends AbstractLavaBoImpl<com.bee.team.fastgo.model.GitlabUserDo, GitlabUserDoMapperExt, com.bee.team.fastgo.model.GitlabUserDoExample> implements GitlabUserBo {

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.admin.privateToken}")
    private String privateToken;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(GitlabUserDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    public BaseSupport baseSupport;

    @Autowired
    private ProjectPublisher publisher;

    @Autowired
    private GitlabUserDoMapperExt gitlabUserDoMapperExt;


    @Override
    public List<UserInfoResVo> getUserInfo() {
        return mapper.findUsersInfo();
    }

    @Override
    public void addGitlabUser(GitlabUserInfoVo gitlabUserInfoVo,UserDo userDo) {
        GitlabAPI gitlabAPI = new GitlabAPI(gitlabUrl,privateToken);
        try{
            GitlabUser gitlibUser = gitlabAPI.createUser(gitlabUserInfoVo.getName(),gitlabUserInfoVo.getEmail(),gitlabUserInfoVo.getUsername(),gitlabUserInfoVo.getPassword());
            if (ObjectUtils.isEmpty(gitlibUser)){
                throw new GlobalException(RES_DATA_NOT_EXIST,"gitlab用户创建失败");
            }
            //调用邮箱工具类，发送通知邮件
            EmailUtil.sendEmail(gitlabUserInfoVo.getEmail(),"新建gitlab用户",EmailUtil.contentTemplate(gitlabUserInfoVo.getName(),gitlabUserInfoVo.getUsername()));
            //添加gitlab用户
            GitlabUserDo gitlabUserDo = new GitlabUserDo();
            gitlabUserDo.setGitlabEmail(gitlibUser.getEmail());
            gitlabUserDo.setGitlabUserName(gitlibUser.getUsername());
            gitlabUserDo.setGitlabName(gitlibUser.getName());
            gitlabUserDo.setGitlabPassword(Md5Utils.getMD5Str(gitlabUserInfoVo.getPassword()));
            gitlabUserDo.setGitlabUserId(gitlibUser.getId());
            gitlabUserDo.setUserId(userDo.getId().intValue());
            gitlabUserDo.setId(null);
            mapper.insertSelective(gitlabUserDo);
        }catch (Exception e){
            throw new GlobalException(RES_DATA_NOT_EXIST,"gitlab用户创建失败");
        }
    }

    @Override
    public void gitlabUserGetProject(GitlabUserGetProjectVo gitlabUserGetProjectVo) {
        List<Integer> userIds = gitlabUserGetProjectVo.getUserIds();
        Integer projectId = gitlabUserGetProjectVo.getProjectId();
        List<ProjectAddMemberVo> projectAddMemberVos = userIds.stream().map(userId -> {
            ProjectAddMemberVo projectAddMemberVo = new ProjectAddMemberVo();
            projectAddMemberVo.setProjectId(projectId);
            projectAddMemberVo.setUserId(userId);
            return projectAddMemberVo;
        }).collect(Collectors.toList());
        //批量添加用户项目关联表
        mapper.insertUserProject(projectAddMemberVos);
        //添加项目分配用户事件
        ProjectAccessEvent projectAccessEvent = new ProjectAccessEvent(new Object(),gitlabUserGetProjectVo.getProjectId(),gitlabUserGetProjectVo.getUserIds(),PROJECT_ACCESS_TYPE1);
        publisher.publish(projectAccessEvent);
    }

    @Override
    public void gitlabUserRemoveProject(GitlabUserGetProjectVo gitlabUserGetProjectVo) {
        List<Integer> userIds = gitlabUserGetProjectVo.getUserIds();
        Integer projectId = gitlabUserGetProjectVo.getProjectId();
        //批量删除用户项目关联表
        mapper.delUserProject(userIds,projectId);
        //添加项目分配用户事件
        ProjectAccessEvent projectAccessEvent = new ProjectAccessEvent(new Object(),gitlabUserGetProjectVo.getProjectId(),gitlabUserGetProjectVo.getUserIds(),PROJECT_ACCESS_TYPE2);
        publisher.publish(projectAccessEvent);
    }

    @Override
    public List<GitlabUserInfoResVo> getGitlabUserInfo() {
        return mapper.findGitlabUsersInfo();
    }

    @Override
    public void distributionGitlabUser(DistributionGitlabUserVo vo) {
        GitlabUserDo gitlabUserDo = gitlabUserDoMapperExt.selectByPrimaryKey(vo.getId().longValue());
        if (gitlabUserDo == null){
            throw new GlobalException(RES_DATA_NOT_EXIST,"gitlab账号不存在");
        }
        String role = mapper.queryUserRole(gitlabUserDo.getUserId());
        if (!role.equals(ADMIN_USER)){
            throw new GlobalException(RES_ILLEGAL_OPERATION,"该gitlab账号已经绑定用户");
        }
        gitlabUserDo.setUserId(vo.getUserId());
        gitlabUserDoMapperExt.updateByPrimaryKeySelective(gitlabUserDo);
    }
}