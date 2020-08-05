package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.StrategyDo;
import com.bee.team.fastgo.model.StrategyDoExample;

public interface StrategyBo extends LavaBo<StrategyDo, StrategyDoExample> {


    /**
      * @param type 类型
     * @param rule 规则
     * @return {@link StrategyDo}
     * @author jgz
     * @date 2020/8/5
     * @desc
     */
    StrategyDo selectByTypeAndRule(String type, String rule);
}