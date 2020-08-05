package com.bee.team.fastgo.controller.project;

import com.bee.team.fastgo.service.project.GitlabUserBo;
import com.bee.team.fastgo.vo.project.UserInfoResVo;
import com.bee.team.fastgo.vo.project.req.GitlabUserGetProjectVo;
import com.bee.team.fastgo.vo.project.req.GitlabUserInfoVo;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hs
 * @date 2020/8/4 9:55
 * @desc gitlab 用户管理
 **/

@Api(tags = "gitlab用户管理")
@RestController
@RequestMapping("/gitUser")
public class GitlabUserController {

    @Autowired
    private GitlabUserBo gitlabUserBo;

    @RequestMapping(value = "/addGitlabUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加新的gitlab用户")
    public ResBody<Void> addGitlabUser(@RequestBody GitlabUserInfoVo gitlabUserInfoVo){
        gitlabUserBo.addGitlabUser(gitlabUserInfoVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/gitlabUserGetProject", method = RequestMethod.POST)
    @ApiOperation(value = "gitlab用户分配项目权限")
    public ResBody<Void> gitlabUserAddProject(@RequestBody GitlabUserGetProjectVo gitlabUserGetProjectVo){
        gitlabUserBo.gitlabUserGetProject(gitlabUserGetProjectVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/gitlabUserDelProject", method = RequestMethod.POST)
    @ApiOperation(value = "gitlab用户删除项目权限")
    public ResBody<Void> gitlabUserDelProject(@RequestBody GitlabUserGetProjectVo gitlabUserGetProjectVo){
        gitlabUserBo.gitlabUserRemoveProject(gitlabUserGetProjectVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户信息")
    public ResBody<List<UserInfoResVo>> getUsersInfo(){
        List<UserInfoResVo> userInfoResVoList = gitlabUserBo.getUserInfo();
        return new ResBody().buildSuccessResBody(userInfoResVoList);
    }

}
