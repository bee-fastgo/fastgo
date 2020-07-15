package com.bee.team.fastgo.service;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ChannelDo;
import com.bee.team.fastgo.model.ChannelDoExample;
import com.bee.team.fastgo.vo.ChannelVo;

import java.util.List;

public interface ChannelBo extends LavaBo<ChannelDo, ChannelDoExample> {

    List<ChannelVo> getData(ChannelVo channelVo);

    ChannelVo getOneData();
}