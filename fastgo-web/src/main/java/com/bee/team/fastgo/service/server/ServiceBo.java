package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import com.bee.team.fastgo.job.core.biz.model.RegistryParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.model.ServiceDo;
import com.bee.team.fastgo.model.ServiceDoExample;
import com.bee.team.fastgo.vo.server.ModifyServiceVo;
import com.bee.team.fastgo.vo.server.ServiceVo;

import java.util.List;

/**
 * 服务器资源管理
 *
 * @author luke
 */
public interface ServiceBo extends LavaBo<ServiceDo, ServiceDoExample> {

    /**
     * @return void
     * @Author luke
     * @Description 手动添加服务器
     * @Date 11:48 2020/7/20 0020
     * @Param [serviceVo]
     **/
    void addServiceDo(ServiceVo serviceVo);

    /**
     * @return void
     * @Author luke
     * @Description 修改服务器配置
     * @Date 11:50 2020/7/20 0020
     * @Param [serviceVo]
     **/
    void modifyServiceDo(ModifyServiceVo modifyServiceVo);

    /**
     * @return com.bee.team.fastgo.model.ServiceDo
     * @Author luke
     * @Description 获取服务器
     * @Date 11:12 2020/7/20 0020
     * @Param [ip]
     **/
    ServiceDo getServiceDoByIp(String ip);

    /**
     * @return void
     * @Author luke
     * @Description 服务自动注册
     * @Date 11:20 2020/7/20 0020
     * @Param [registryParam]
     **/
    ReturnT<String> registry(RegistryParam registryParam);

    /**
     * @return void
     * @Author luke
     * @Description 服务断开连接
     * @Date 11:25 2020/7/20 0020
     * @Param [registryParam]
     **/
    ReturnT<String> registryRemove(RegistryParam registryParam);
}