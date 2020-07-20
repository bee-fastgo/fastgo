package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.job.core.biz.model.RegistryParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.mapper.ServiceDoMapperExt;
import com.bee.team.fastgo.model.ServiceDo;
import com.bee.team.fastgo.model.ServiceDoExample;
import com.bee.team.fastgo.service.server.ServiceBo;
import com.bee.team.fastgo.vo.server.ModifyServiceVo;
import com.bee.team.fastgo.vo.server.ServiceVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ServiceBoImpl extends AbstractLavaBoImpl<ServiceDo, ServiceDoMapperExt, ServiceDoExample> implements ServiceBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    public void setBaseMapper(ServiceDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    @ValidHandler(key = "serviceVo", value = ServiceVo.class, isReqBody = false)
    public void addServiceDo(ServiceVo serviceVo) {
        ServiceDo serviceDo = baseSupport.objectCopy(serviceVo, ServiceDo.class);
        insert(serviceDo);
    }

    @Override
    @ValidHandler(key = "modifyServiceVo", value = ServiceVo.class, isReqBody = false)
    public void modifyServiceDo(ModifyServiceVo modifyServiceVo) {
        getServiceDoByIp(modifyServiceVo.getServerIp());
        ServiceDo serviceDo = baseSupport.objectCopy(modifyServiceVo, ServiceDo.class);
        update(serviceDo);
    }

    @Override
    public ServiceDo getServiceDoByIp(String ip) {
        ServiceDoExample serviceDoExample = new ServiceDoExample();
        serviceDoExample.createCriteria().andServerIpEqualTo(ip);
        List<ServiceDo> serviceDoList = this.mapper.selectByExample(serviceDoExample);
        if (CollectionUtils.isEmpty(serviceDoList)) {
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "服务器资源不存在");
        }
        return serviceDoList.get(0);
    }


    @Override
    public ReturnT<String> serviceRegistry(RegistryParam registryParam) {
        // valid
        if (!StringUtils.hasText(registryParam.getRegistryGroup())
                || !StringUtils.hasText(registryParam.getRegistryKey())
                || !StringUtils.hasText(registryParam.getRegistryValue())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "Illegal Argument.");
        }
        ServiceDoExample serviceDoExample = new ServiceDoExample();
        serviceDoExample.createCriteria().andServerIpEqualTo(registryParam.getRegistryValue());
        List<ServiceDo> serviceDoList = this.mapper.selectByExample(serviceDoExample);
        if (CollectionUtils.isEmpty(serviceDoList)) {
            // 自动注册
            ServiceDo serviceDo = new ServiceDo();
            serviceDo.setServerName(registryParam.getRegistryGroup());
            serviceDo.setServerIp(registryParam.getRegistryValue());
            serviceDo.setServerStatus(CommonConstant.CODE2);
            serviceDo.setType(CommonConstant.CODE2);
            serviceDo.setServiceToken(registryParam.getRegistryKey());
            insert(serviceDo);
        } else {
            // 更新服务状态
            ServiceDo serviceDo = serviceDoList.get(0);
            serviceDo.setServerStatus(CommonConstant.CODE2);
            update(serviceDo);
        }
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        // valid
        if (!StringUtils.hasText(registryParam.getRegistryGroup())
                || !StringUtils.hasText(registryParam.getRegistryKey())
                || !StringUtils.hasText(registryParam.getRegistryValue())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "Illegal Argument.");
        }
        ServiceDoExample serviceDoExample = new ServiceDoExample();
        serviceDoExample.createCriteria().andServerIpEqualTo(registryParam.getRegistryValue());
        List<ServiceDo> serviceDoList = this.mapper.selectByExample(serviceDoExample);
        if (CollectionUtils.isEmpty(serviceDoList)) {
            // 自动注册
            ServiceDo serviceDo = new ServiceDo();
            serviceDo.setServerName(registryParam.getRegistryGroup());
            serviceDo.setServerIp(registryParam.getRegistryValue());
            serviceDo.setServerStatus(CommonConstant.CODE1);
            serviceDo.setType(CommonConstant.CODE2);
            serviceDo.setServiceToken(registryParam.getRegistryKey());
            insert(serviceDo);
        } else {
            // 更新服务状态
            ServiceDo serviceDo = serviceDoList.get(0);
            serviceDo.setServerStatus(CommonConstant.CODE1);
            update(serviceDo);
        }
        return ReturnT.SUCCESS;
    }
}