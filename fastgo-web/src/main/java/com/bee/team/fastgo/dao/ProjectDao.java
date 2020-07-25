package com.bee.team.fastgo.dao;

import com.bee.team.fastgo.model.ProjectDo;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.bee.team.fastgo.vo.project.req.InsertBackProjectProfileVo;

public interface ProjectDao {

    /**
     * 生成simple框架模板
     * @return
     */
    String generateSimpleTemplate(ProjectDo projectDo);

    /**
     * 上传后台模板代码到gitlab项目中
     * @param gitlabProjectDo
     * @param filePath
     * @return
     */
    void uploadBackCodeIntoGitlab(GitlabProjectDo gitlabProjectDo,String filePath);

    /**
     * 上传前台模板代码到gitlab项目中
     * @param gitlabProjectDo
     * @return
     */
    void uploadFrontCodeIntoGitlab(GitlabProjectDo gitlabProjectDo,String filePath);

    /**
     * @param insertBackProjectProfileVo
     * @return {@link Integer}
     * @author hs
     * @date 2020/7/25
     * @desc 创建新的项目环境
     */

    Integer addProjectProfile(InsertBackProjectProfileVo insertBackProjectProfileVo);

}
