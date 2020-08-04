package com.bee.team.fastgo.timer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.common.MonitorTypeConstant;
import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.biz.ExecutorBiz;
import com.bee.team.fastgo.job.core.biz.model.LogParam;
import com.bee.team.fastgo.job.core.biz.model.LogResult;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.server.core.scheduler.SimpleJobScheduler;
import com.bee.team.fastgo.service.server.*;
import com.bee.team.fastgo.vo.server.ServerVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jgz
 * @date 2020/8/4
 * @desc 监控定时调度
 **/
@Configuration
@EnableScheduling
public class MonitorTimer {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduling = new ThreadPoolTaskScheduler();
        scheduling.setPoolSize(10);
        scheduling.initialize();
        return scheduling;
    }

    @Autowired
    private BaseSupport baseSupport;
    @Autowired
    private ServerBo serverBo;
    @Autowired
    private AlertInfoBo alertInfoBo;
    @Autowired
    private ServerRunProfileBo serverRunProfileBo;

    @Autowired
    private ServerExecutorLogBo serverExecutorLogBo;

    @Autowired
    private ServerSoftwareProfileBo serverSoftwareProfileBo;


    private List<SelectType> list = new CopyOnWriteArrayList<>();

    /**
     * 每分钟执行一次任务调度
     *
     * @author jgz
     * @date 2020/8/4
     * @desc
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void execute() {
        //软件环境监控
        List<ServerSoftwareProfileDo> serverSoftwareProfileDoList = serverSoftwareProfileBo.getAll();
        serverSoftwareProfileDoList.forEach(serverSoftwareProfileDo -> {
            JSONObject jsonObject = JSON.parseObject(serverSoftwareProfileDo.getSoftwareConfig());
            String selectId = SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, "#!/bin/bash\n" +
                    "\n" +
                    "if [[ -z $(lsof -i:" + jsonObject.get("port") + ") ]];then\n" +
                    "        echo \"{\"ip\":\"" + jsonObject.get("ip") + "\",\"port\":\"" + jsonObject.get("port") + "\",\"name\":\"" + serverSoftwareProfileDo.getSoftwareName() + "\",\"status\":\"died\"}\"\n" +
                    "else\n" +
                    "        echo \"{\"ip\":\"" + jsonObject.get("ip") + "\",\"port\":\"" + jsonObject.get("port") + "\",\"name\":\"" + serverSoftwareProfileDo.getSoftwareName() + "\",\"status\":\"survive\"}\"\n" +
                    "fi", null, -1, serverSoftwareProfileDo.getServerIp());
            SelectType selectType = new SelectType();
            selectType.setSelectId(selectId);
            selectType.setType(MonitorTypeConstant.SOFTWARE);
            list.add(selectType);
        });

//        //运行环境监控
//        List<ServerRunProfileDo> serverRunProfileDoList = serverRunProfileBo.getListServerRunProfileDo();
//        serverRunProfileDoList.forEach(serverRunProfileDo -> {
//            JSONObject jsonObject = JSON.parseObject(serverRunProfileDo.getSoftwareConfig());
//            String selectId = SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, "#!/bin/bash\n" +
//                    "\n" +
//                    "if [[ -z $(docker ps | " + serverRunProfileDo.getSoftwareConfig() + "Docker) ]];then\n" +
//                    "        echo \"{\"ip\":\"" + jsonObject.get("ip") + "\",\"port\":\"" + jsonObject.get("port") + "\",\"name\":\"" + serverRunProfileDo.getSoftwareName() + "\",\"status\":\"died\"}\"\n" +
//                    "else\n" +
//                    "        echo \"{\"ip\":\"" + jsonObject.get("ip") + "\",\"port\":\"" + jsonObject.get("port") + "\",\"name\":\"" + serverRunProfileDo.getSoftwareName() + "\",\"status\":\"survive\"}\"\n" +
//                    "fi", null, -1, serverRunProfileDo.getServerIp());
//            SelectType selectType = new SelectType();
//            selectType.setSelectId(selectId);
//            selectType.setType(MonitorTypeConstant.PROJECT);
//            list.add(selectType);
//        });

        list.removeIf(selectType -> {
            ServerExecutorLogDo serverExecutorLogDo = serverExecutorLogBo.getServerExecutorLogDoById(selectType.getSelectId());
            String address = "http://" + serverExecutorLogDo.getExecutorAddress() + ":" + "9999/";
            ExecutorBiz executorBiz = null;
            try {
                executorBiz = SimpleJobScheduler.getExecutorBiz(address);
                ReturnT<LogResult> logResult = executorBiz.log(new LogParam(serverExecutorLogDo.getTriggerTime().getTime(), Long.parseLong(selectType.getSelectId()), 1));
                //TODO 接入告警
                LogResult content = logResult.getContent();
                AlertInfoDo alertInfoDo = new AlertInfoDo();
                alertInfoDo.setType(selectType.getType());
                alertInfoDo.setInfo(content.getLogContent());
                alertInfoBo.insertAlertInfo(alertInfoDo);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * 服务是否存活
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void serverIsLive() {
        List<ServerVo> serverVos = serverBo.queryListServer();
        if (CollectionUtils.isEmpty(serverVos)) {
            return;
        }
        for (ServerVo serverVo : serverVos) {
            boolean hostConnectable = isHostConnectable(serverVo.getServerIp(), 9999);
            if (hostConnectable == false) {
                ServerDo serverDo = baseSupport.objectCopy(serverVo, ServerDo.class);
                serverDo.setServerStatus(CommonConstant.CODE1);
                serverBo.update(serverDo);
            }
        }
    }

    /**
     * 服务是否存在
     *
     * @param host
     * @return
     */
    private boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static class SelectType {

        private String selectId;

        private String type;

        public String getSelectId() {
            return selectId;
        }

        public void setSelectId(String selectId) {
            this.selectId = selectId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


}
