package com.bee.team.fastgo.service.server.impl;

import ch.ethz.ssh2.Connection;
import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.job.core.biz.model.RegistryParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.mapper.ServerDoMapperExt;
import com.bee.team.fastgo.model.ServerDo;
import com.bee.team.fastgo.model.ServerDoExample;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.tools.deploy.scp.Scp;
import com.bee.team.fastgo.vo.server.AddServerVo;
import com.bee.team.fastgo.vo.server.ModifyServerVo;
import com.bee.team.fastgo.vo.server.QueryServerVo;
import com.bee.team.fastgo.vo.server.ServerVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import com.spring.simple.development.support.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.spring.simple.development.support.exception.GlobalResponseCode.SERVICE_FAILED;

/**
 * @author luke
 */
@Service
public class ServerBoImpl extends AbstractLavaBoImpl<ServerDo, ServerDoMapperExt, ServerDoExample> implements ServerBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    public void setBaseMapper(ServerDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void addServerDo(AddServerVo addServerVo) {
        ServerDo dbServerDo = getServerDoByIp(addServerVo.getServerIp());
        if (dbServerDo != null) {
            throw new GlobalException(ResponseCode.RES_DATA_EXIST, "服务器资源已存在");
        }
        ServerDo ServerDo = baseSupport.objectCopy(addServerVo, ServerDo.class);
        ServerDo.setServerStatus(CommonConstant.CODE1);
        ServerDo.setType(CommonConstant.CODE1);
        insert(ServerDo);

        Connection conn = null;
        try {
            conn = new Connection(addServerVo.getServerIp(), addServerVo.getSshPort());
            System.out.println(DateUtils.getCurrentTime() + "开始远程传输文件");
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(addServerVo.getSshUser(), addServerVo.getSshPassword());
            if (!isAuthenticated) {
                throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
            }
            String cmd = "yum -y install wget && wget http://172.22.5.73/software/init.tar.gz && tar -zxf init.tar.gz && bash ./init/install_jdk.sh ";
            Scp.invokeCmd(conn.openSession(), cmd);
        }
        catch (IOException e) {
            throw new GlobalException(SERVICE_FAILED);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

//        try {
//            initServer(ServerDo.getServerIp(),
//                    ServerDo.getSshPort(),
//                    ServerDo.getSshUser(),
//                    ServerDo.getSshPassword(),
//                    "yum -y install wget && wget http://172.22.5.73/software/init.tar.gz && tar -zxf init.tar.gz && bash ./init/install_jdk.sh");
//        } catch (JSchException e) {
//            throw new GlobalException(ResponseCode.RES_DATA_EXIST, "初始化服务器失败");
//        }
    }

    @Override
    public void modifyServerDo(ModifyServerVo modifyServerVo) {
        ServerDo dbServerDo = getServerDoByIp(modifyServerVo.getServerIp());
        if (dbServerDo == null) {
            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "服务器资源不存在");
        }
        ServerDo ServerDo = baseSupport.objectCopy(modifyServerVo, ServerDo.class);
        update(ServerDo);
    }

    @Override
    public ResPageDTO queryPageServer(QueryServerVo queryServerVo) {
        ServerDoExample serverDoExample = new ServerDoExample();
        if (!StringUtils.isEmpty(queryServerVo.getParams())) {
            serverDoExample.createCriteria().andServerNameLike(queryServerVo.getParams());
        }
        // 设置分页
        int startNo = queryServerVo.getStartPage();
        int rowCount = queryServerVo.getPageSize();
        PageHelper.startPage(startNo, rowCount);
        // 查询
        List<ServerDo> serverDoList = this.mapper.selectByExample(serverDoExample);
        return baseSupport.pageCopy(new PageInfo(serverDoList), ServerVo.class);
    }

    @Override
    public List<ServerVo> queryListServer() {
        ServerDoExample serverDoExample = new ServerDoExample();
        List<ServerDo> serverDoList = this.mapper.selectByExample(serverDoExample);
        return baseSupport.listCopy(serverDoList,ServerVo.class);
    }

    @Override
    public ServerDo getServerDoByIp(String ip) {
        ServerDoExample ServerDoExample = new ServerDoExample();
        ServerDoExample.createCriteria().andServerIpEqualTo(ip);
        List<ServerDo> ServerDoList = this.mapper.selectByExample(ServerDoExample);
        if (CollectionUtils.isEmpty(ServerDoList)) {
            return null;
        }
        return ServerDoList.get(0);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        // valid
        if (!StringUtils.hasText(registryParam.getRegistryGroup())
                || !StringUtils.hasText(registryParam.getRegistryKey())
                || !StringUtils.hasText(registryParam.getRegistryValue())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "Illegal Argument.");
        }
        ServerDoExample ServerDoExample = new ServerDoExample();
        ServerDoExample.createCriteria().andServerIpEqualTo(registryParam.getRegistryValue().replaceAll("http://", "").replaceAll(":9999/", ""));
        List<ServerDo> ServerDoList = this.mapper.selectByExample(ServerDoExample);
        if (CollectionUtils.isEmpty(ServerDoList)) {
            // 自动注册
            ServerDo ServerDo = new ServerDo();
            ServerDo.setServerName(registryParam.getRegistryGroup());
            ServerDo.setServerIp(registryParam.getRegistryValue().replaceAll("http://", "").replaceAll(":9999/", ""));
            ServerDo.setServerStatus(CommonConstant.CODE2);
            ServerDo.setType(CommonConstant.CODE2);
            ServerDo.setClientName(registryParam.getRegistryKey());
            insert(ServerDo);
        } else {
            // 更新服务状态
            ServerDo ServerDo = ServerDoList.get(0);
            ServerDo.setServerStatus(CommonConstant.CODE2);
            update(ServerDo);
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
        ServerDoExample ServerDoExample = new ServerDoExample();
        ServerDoExample.createCriteria().andServerIpEqualTo(registryParam.getRegistryValue().replaceAll("http://", "").replaceAll(":9999/", ""));
        List<ServerDo> ServerDoList = this.mapper.selectByExample(ServerDoExample);
        if (CollectionUtils.isEmpty(ServerDoList)) {
            // 自动注册
            ServerDo ServerDo = new ServerDo();
            ServerDo.setServerName(registryParam.getRegistryGroup());
            ServerDo.setServerIp(registryParam.getRegistryValue().replaceAll("http://", "").replaceAll(":9999/", ""));
            ServerDo.setServerStatus(CommonConstant.CODE1);
            ServerDo.setType(CommonConstant.CODE2);
            ServerDo.setClientName(registryParam.getRegistryKey());
            insert(ServerDo);
        } else {
            // 更新服务状态
            ServerDo ServerDo = ServerDoList.get(0);
            ServerDo.setServerStatus(CommonConstant.CODE1);
            update(ServerDo);
        }
        return ReturnT.SUCCESS;
    }


    private void initServer(String host, int port, String user, String password, String command) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setCommand(command);
        channelExec.setErrStream(System.err);
        channelExec.connect();

        channelExec.disconnect();
        session.disconnect();
    }

}