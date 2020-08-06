package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.StrategyDoMapperExt;
import com.bee.team.fastgo.model.StrategyDo;
import com.bee.team.fastgo.model.StrategyDoExample;
import com.bee.team.fastgo.service.server.StrategyBo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyBoImpl extends AbstractLavaBoImpl<StrategyDo, StrategyDoMapperExt, StrategyDoExample> implements StrategyBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(StrategyDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public StrategyDo selectByTypeAndRule(String type, String rule) {
        StrategyDoExample strategyDoExample = new StrategyDoExample();
        strategyDoExample.createCriteria().andTypeEqualTo(type).andRuleEqualTo(rule);
        List<StrategyDo> strategyDoList = selectByExample(strategyDoExample);
        if (CollectionUtils.isEmpty(strategyDoList)){
            return null;
        }
        return strategyDoList.get(0);
    }
}