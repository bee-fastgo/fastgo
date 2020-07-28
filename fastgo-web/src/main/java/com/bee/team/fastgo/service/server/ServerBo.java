package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.job.core.biz.model.RegistryParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.model.ServerDo;
import com.bee.team.fastgo.model.ServerDoExample;
import com.bee.team.fastgo.vo.server.AddServerVo;
import com.bee.team.fastgo.vo.server.ModifyServerVo;
import com.bee.team.fastgo.vo.server.QueryServerVo;
import com.bee.team.fastgo.vo.server.ServerVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.List;

public interface ServerBo extends LavaBo<ServerDo, ServerDoExample> {
    /**
     * @return void
     * @Author luke
     * @Description 手动添加服务器
     * @Date 11:48 2020/7/20 0020
     * @Param [serviceVo]
     **/
    void addServerDo(AddServerVo addServerVo);

    /**
     * @return void
     * @Author luke
     * @Description 修改服务器配置
     * @Date 11:50 2020/7/20 0020
     * @Param [serviceVo]
     **/
    void modifyServerDo(ModifyServerVo modifyServerVo);

    /**
     * @return com.spring.simple.development.core.component.mvc.page.ResPageDTO
     * @Author luke
     * @Description 查询服务器列表
     * @Date 9:29 2020/7/21 0021
     * @Param [queryServerVo]
     **/
    ResPageDTO queryPageServer(QueryServerVo queryServerVo);


    /**
     * @return com.spring.simple.development.core.component.mvc.page.ResPageDTO
     * @Author luke
     * @Description 查询服务器列表
     * @Date 9:29 2020/7/21 0021
     * @Param [queryServerVo]
     **/
    List<ServerVo> queryListServer();

    /**
     * @return com.bee.team.fastgo.model.ServiceDo
     * @Author luke
     * @Description 获取服务器
     * @Date 11:12 2020/7/20 0020
     * @Param [ip]
     **/
    ServerDo getServerDoByIp(String ip);

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