package com.bee.team.fastgo.service;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.DemoDoExample;
import com.bee.team.fastgo.vo.DemoVo;
import com.bee.team.fastgo.model.DemoDo;

public interface TestDemoBo extends LavaBo<DemoDo, DemoDoExample> {

    /**
     * 插入
     * @param demoVo
     */
    int insertData(DemoVo demoVo);

    /**
     * 插入
     * @param id
     */
    int deleteData(String id);

    /**
     * 查询
     * @param id
     */
    DemoVo getData(String id);
}