package com.bee.team.fastgo.dao;

import com.bee.team.fastgo.model.ProjectDo;

public interface ProjectDao {

    /**
     * 生成simple框架模板
     * @return
     */
    String generateSimpleTemplate(ProjectDo projectDo);

}
