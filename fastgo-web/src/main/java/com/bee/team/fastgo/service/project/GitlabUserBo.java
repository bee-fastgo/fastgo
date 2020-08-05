package com.bee.team.fastgo.service.project;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.GitlabUserDo;
import com.bee.team.fastgo.model.GitlabUserDoExample;
import com.bee.team.fastgo.vo.project.UserInfoResVo;
import com.bee.team.fastgo.vo.project.req.GitlabUserGetProjectVo;
import com.bee.team.fastgo.vo.project.req.GitlabUserInfoVo;

import java.util.List;

public interface GitlabUserBo extends com.alibaba.lava.base.LavaBo<com.bee.team.fastgo.model.GitlabUserDo, com.bee.team.fastgo.model.GitlabUserDoExample> {
    /**
     * @param
     * @return {@link List< UserInfoResVo>}
     * @author hs
     * @date 2020/8/4
     * @desc 获取所有用户信息
     */

    List<UserInfoResVo> getUserInfo();

    /**
     * @param gitlabUserInfoVo
     * @return
     * @author hs
     * @date 2020/8/4
     * @desc 新增gitlab用户
     */

    void addGitlabUser(GitlabUserInfoVo gitlabUserInfoVo);

    /**
     * @param gitlabUserGetProjectVo
     * @return
     * @author hs
     * @date 2020/8/4
     * @desc gitlab用户分配项目
     */

    void gitlabUserGetProject(GitlabUserGetProjectVo gitlabUserGetProjectVo);

    /**
     * @param gitlabUserGetProjectVo
     * @return
     * @author hs
     * @date 2020/8/4
     * @desc gitlab用户移除项目
     */
    void gitlabUserRemoveProject(GitlabUserGetProjectVo gitlabUserGetProjectVo);
}