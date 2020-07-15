package com.bee.team.fastgo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.alibaba.lava.privilege.PrivilegeInfo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.annotation.base.swagger.Api;
import com.spring.simple.development.core.annotation.base.swagger.ApiImplicitParam;
import com.spring.simple.development.core.annotation.base.swagger.ApiOperation;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.bee.team.fastgo.mapper.DemoDoMapperExt;
import com.bee.team.fastgo.model.DemoDo;
import com.bee.team.fastgo.model.DemoDoExample;
import com.bee.team.fastgo.service.TestDemoBo;
import com.bee.team.fastgo.vo.DemoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@IsApiService(isLogin = false)
@Api(tags = "用户相关11111")
public class TestDemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements TestDemoBo {

    @Autowired
    private BaseSupport baseSupport;
    @Autowired
    PrivilegeInfo privilegeInfo;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    @CacheEvict(value = "test", key = "#demoVo.id", condition = "#demoVo != null")
    @ApiOperation(value = "插入", notes = "插入一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo")
    public int insertData(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        return insert(demoDo);
    }

    @Override
    @CacheEvict(value = "test", key = "#id", condition = "#id != null")
    @ApiOperation(value = "删除", notes = "删除一亿个订单")
    @ValidHandler(key = "demoVo", value = DemoVo.class, isReqBody = false)
    public int deleteData(String id) {
        return delete(Long.valueOf(id));
    }

    @Override
    @Cacheable(value = "test", key = "#id", condition = "#id != null",unless="#result == null")
    @ApiOperation(value = "查询", notes = "查询一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo", resultDataType = DemoVo.class)
    public DemoVo getData(String id) {
        System.out.println(privilegeInfo);
        DemoDo demoDo = selectByPrimaryKey(Long.valueOf(id));
        return baseSupport.objectCopy(demoDo, DemoVo.class);
    }
}