package com.bee.team.fastgo.mapper;

import com.bee.team.fastgo.vo.project.GitlabUserInfoResVo;
import com.bee.team.fastgo.vo.project.UserInfoResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GitlabUserDoMapperExt extends com.alibaba.lava.base.LavaMapper<com.bee.team.fastgo.model.GitlabUserDo, com.bee.team.fastgo.model.GitlabUserDoExample> {

    /**
     * @param
     * @return {@link List< UserInfoResVo>}
     * @author hs
     * @date 2020/8/4
     * @desc 查询所有用户的用户名
     */

    List<UserInfoResVo> findUsersInfo();


    /**
     * @param maps
     * @return
     * @author hs
     * @date 2020/8/4
     * @desc 批量添加用户项目关联表
     */

    void insertUserProject(List<Map<String, Object>> maps);

    /**
     * @param userIds
     * @param projectId
     * @return
     * @author hs
     * @date 2020/8/4
     * @desc 批量删除用户项目关联表
     */

    void delUserProject(@Param("userIds") List<Integer> userIds, @Param("projectId") Integer projectId);

    /**
     * @param
     * @return {@link List< UserInfoResVo>}
     * @author hs
     * @date 2020/8/4
     * @desc 查询所有Gitlab用户信息
     */

    List<GitlabUserInfoResVo> findGitlabUsersInfo();
}