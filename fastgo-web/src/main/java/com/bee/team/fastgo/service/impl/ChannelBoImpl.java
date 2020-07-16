package com.bee.team.fastgo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.bee.team.fastgo.mapper.ChannelDoMapperExt;
import com.bee.team.fastgo.model.ChannelDo;
import com.bee.team.fastgo.model.ChannelDoExample;
import com.bee.team.fastgo.service.ChannelBo;
import com.bee.team.fastgo.vo.ChannelVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelBoImpl extends AbstractLavaBoImpl<ChannelDo, ChannelDoMapperExt, ChannelDoExample> implements ChannelBo {

    @Autowired
    public void setBaseMapper(ChannelDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    private BaseSupport baseSupport;

    @Override
    public List<ChannelVo> getData(ChannelVo channelVo) {
        return null;
    }

    @Override
    public ChannelVo getOneData() {
        ChannelDoExample channelDoExample = new ChannelDoExample();
        ChannelDo channelDo = this.mapper.selectByPrimaryKey(1L);
        this.delete(1L);
        this.update(channelDo);
        return baseSupport.objectCopy(this.mapper.selectByPrimaryKey(1L), ChannelVo.class);
    }
}