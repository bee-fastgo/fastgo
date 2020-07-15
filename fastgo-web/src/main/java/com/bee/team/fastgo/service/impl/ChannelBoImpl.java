package com.bee.team.fastgo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.dpo.ChannelDpo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.data.process.hander.DataProcessHelper;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.bee.team.fastgo.mapper.ChannelDoMapperExt;
import com.bee.team.fastgo.model.ChannelDo;
import com.bee.team.fastgo.model.ChannelDoExample;
import com.bee.team.fastgo.service.ChannelBo;
import com.bee.team.fastgo.vo.ChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@IsApiService
public class ChannelBoImpl extends AbstractLavaBoImpl<ChannelDo, ChannelDoMapperExt, ChannelDoExample> implements ChannelBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ChannelDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    private BaseSupport baseSupport;
    @Autowired
    private DataProcessHelper dataProcessHelper;

    @Override
    public List<ChannelVo> getData(ChannelVo channelVo) {
        ChannelDpo channelDpo = baseSupport.objectCopy(channelVo, ChannelDpo.class);
        List<ChannelDo> executor = dataProcessHelper.executor(channelDpo, ChannelDo.class);
        return baseSupport.listCopy(executor, ChannelVo.class);
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