package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.AlertInfoDoMapperExt;
import com.bee.team.fastgo.model.AlertInfoDo;
import com.bee.team.fastgo.model.AlertInfoDoExample;
import com.bee.team.fastgo.service.server.AlertInfoBo;
import com.bee.team.fastgo.vo.server.QueryAlertInfoVo;
import com.bee.team.fastgo.vo.server.ResAlertInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlertInfoBoImpl extends AbstractLavaBoImpl<AlertInfoDo, AlertInfoDoMapperExt, AlertInfoDoExample> implements AlertInfoBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(AlertInfoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public ResPageDTO<ResAlertInfoVo> getAlertInfoByPage(QueryAlertInfoVo queryAlertInfoVo) {
        AlertInfoDoExample alertInfoDoExample = new AlertInfoDoExample();
        int startNo = queryAlertInfoVo.getStartPage();
        int rowCount = queryAlertInfoVo.getPageSize();
        PageHelper.startPage(startNo, rowCount);
        List<AlertInfoDo> alertInfoDoList = selectByExample(alertInfoDoExample);
        return baseSupport.pageCopy(new PageInfo(alertInfoDoList), ResAlertInfoVo.class);
    }

    @Override
    public void insertAlertInfo(AlertInfoDo alertInfoDo) {
        alertInfoDo.setCode(RandomUtil.randomAll(16));
        alertInfoDo.setAlertTime(new Date());
        insert(alertInfoDo);
    }

}