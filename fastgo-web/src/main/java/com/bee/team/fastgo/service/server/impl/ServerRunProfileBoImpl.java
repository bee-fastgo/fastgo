package com.bee.team.fastgo.service.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.mapper.ProfileRunprofileRelationDoMapperExt;
import com.bee.team.fastgo.mapper.ServerExecutorLogDoMapperExt;
import com.bee.team.fastgo.mapper.ServerRunProfileDoMapperExt;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.model.ServerRunProfileDo;
import com.bee.team.fastgo.model.ServerRunProfileDoExample;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerRunProfileBo;
import com.bee.team.fastgo.vo.server.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import com.spring.simple.development.support.utils.RandomUtil;
import com.spring.simple.development.support.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author luke
 * @desc 运行环境
 * @date 2020-07-29
 **/
@Service
public class ServerRunProfileBoImpl extends AbstractLavaBoImpl<ServerRunProfileDo, ServerRunProfileDoMapperExt, ServerRunProfileDoExample> implements ServerRunProfileBo {
    @Autowired
    private ServerBo serverBo;
    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ProfileRunprofileRelationDoMapperExt profileRunprofileRelationDoMapperExt;

    @Autowired
    public void setBaseMapper(ServerRunProfileDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public ServerRunProfileDo addServerRunProfileDo(AddServerRunProfileVo addServerRunProfileVo) {
        ServerDo serverDo = serverBo.getServerDoByIp(addServerRunProfileVo.getServerIp());
        if (serverDo == null) {
            throw new GlobalException(ResponseCode.RES_DATA_NOT_EXIST, "服务器资源不存在");
        }
        int port = isHostConnectable(addServerRunProfileVo.getServerIp());

        ServerRunProfileDo dbServerRunProfileDo = getServerRunProfileDo(addServerRunProfileVo.getServerIp(), port);
        if (dbServerRunProfileDo != null) {
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "服务器资源分配错误,请重试");
        }
        ServerRunProfileDo serverRunProfileDo = new ServerRunProfileDo();
        serverRunProfileDo.setServerIp(addServerRunProfileVo.getServerIp());
        serverRunProfileDo.setSoftwareCode(RandomUtils.getRandomStr(8));
        serverRunProfileDo.setSoftwareName(addServerRunProfileVo.getSoftwareName());
        RunProfileVo runProfileVo = new RunProfileVo();
        runProfileVo.setIp(addServerRunProfileVo.getServerIp());
        runProfileVo.setPort(String.valueOf(port));
        serverRunProfileDo.setSoftwareConfig(JSONObject.toJSONString(runProfileVo));
        serverRunProfileDo.setRunProfileCode(SoftwareEnum.DOCKER.name());
        insert(serverRunProfileDo);
        return serverRunProfileDo;
    }

    @Override
    public void delServerRunProfileDo(DelServerRunProfileVo delServerRunProfileVo) {
        ProfileRunprofileRelationDoExample profileRunprofileRelationDoExample = new ProfileRunprofileRelationDoExample();
        profileRunprofileRelationDoExample.createCriteria().andRunProfileCodeEqualTo(delServerRunProfileVo.getRunProfileCode());
        List<ProfileRunprofileRelationDo> profileRunprofileRelationDos = profileRunprofileRelationDoMapperExt.selectByExample(profileRunprofileRelationDoExample);
        if (!CollectionUtils.isEmpty(profileRunprofileRelationDos)) {
            throw new GlobalException(ResponseCode.RES_DATA_EXIST, "运行环境已关联项目");
        }
        ServerRunProfileDoExample serverRunProfileDoExample = new ServerRunProfileDoExample();
        serverRunProfileDoExample.createCriteria().andRunProfileCodeEqualTo(delServerRunProfileVo.getRunProfileCode());
        deleteByExample(serverRunProfileDoExample);
    }

    @Override
    public ServerRunProfileDo getServerRunProfileDo(String ip, int port) {
        ServerRunProfileDoExample serverRunProfileDoExample = new ServerRunProfileDoExample();
        serverRunProfileDoExample.createCriteria().andServerIpEqualTo(ip);
        List<ServerRunProfileDo> serverRunProfileDos = this.mapper.selectByExample(serverRunProfileDoExample);
        if (CollectionUtils.isEmpty(serverRunProfileDos)) {
            return null;
        }
        for (ServerRunProfileDo serverRunProfileDo : serverRunProfileDos) {
            RunProfileVo runProfileVo = JSONObject.parseObject(serverRunProfileDo.getSoftwareConfig(), RunProfileVo.class);
            if (runProfileVo.getPort().equals(String.valueOf(port))) {
                return serverRunProfileDo;
            }
        }
        return null;
    }

    @Override
    public ResPageDTO queryPageServerRunProfileDo(QueryRunProfileVo queryRunProfileVo) {
        ServerRunProfileDoExample serverRunProfileDoExample = new ServerRunProfileDoExample();

        // 设置分页
        int startNo = queryRunProfileVo.getStartPage();
        int rowCount = queryRunProfileVo.getPageSize();
        PageHelper.startPage(startNo, rowCount);
        // 查询
        List<ServerRunProfileDo> serverRunProfileDoList = this.mapper.selectByExample(serverRunProfileDoExample);
        return baseSupport.pageCopy(new PageInfo(serverRunProfileDoList), ServerRunProfileVo.class);
    }

    /**
     * 端口是否占用
     *
     * @param host
     * @return
     */
    private int isHostConnectable(String host) {
        int port = (int) (Math.random() * (65535 - 1024 + 1) + 1024);
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            int i = 3;
            while (i > 0) {
                port = (int) (Math.random() * (65535 - 1024 + 1) + 1024);
                socket.connect(new InetSocketAddress(host, (int) (Math.random() * (65535 - 1024 + 1) + 1024)));
                i--;
            }
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "服务器资源分配错误,请重试");
        } catch (IOException e) {
            return port;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
