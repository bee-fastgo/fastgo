package com.bee.team.fastgo.timer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.common.MonitorTypeConstant;
import com.bee.team.fastgo.hander.alert.AlertBody;
import com.bee.team.fastgo.hander.alert.AlertHandler;
import com.bee.team.fastgo.hander.event.EventPublisher;
import com.bee.team.fastgo.model.ServerDo;
import com.bee.team.fastgo.model.ServerRunProfileDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerRunProfileBo;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.bee.team.fastgo.vo.server.ServerVo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private AlertHandler alertHandler;

    @Autowired
    private ServerRunProfileBo serverRunProfileBo;

    @Autowired
    private ServerSoftwareProfileBo serverSoftwareProfileBo;


    /**
     * 每5分钟执行一次项目
     *
     * @author jgz
     * @date 2020/8/4
     * @desc
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void projectMonitor() {

        //运行环境监控
        List<ServerRunProfileDo> serverRunProfileDoList = serverRunProfileBo.getListServerRunProfileDo();
        serverRunProfileDoList.forEach(serverRunProfileDo -> {
            JSONObject jsonObject = JSON.parseObject(serverRunProfileDo.getSoftwareConfig());
            //如果无法连接
            if(!isHostConnectable((String) jsonObject.get("ip"), Integer.parseInt((String) jsonObject.get("port")))){
                AlertBody alertBody = new AlertBody();
                alertBody.setType(MonitorTypeConstant.PROJECT);
                Map<String,String> info = new HashMap<>(4);
                info.put("ip", (String) jsonObject.get("ip"));
                info.put("port",(String) jsonObject.get("port"));
                info.put("name",serverRunProfileDo.getSoftwareName());
                info.put("status","death");
                alertBody.setInfo(info);
                //告警并入库
                alertHandler.alert(alertBody);
            }
        });

    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void softwareMonitor() {
        //软件环境监控
        List<ServerSoftwareProfileDo> serverSoftwareProfileDoList = serverSoftwareProfileBo.getAll();
        serverSoftwareProfileDoList.forEach(serverSoftwareProfileDo -> {
            JSONObject jsonObject = JSON.parseObject(serverSoftwareProfileDo.getSoftwareConfig());
            //如果无法连接
            if(!isHostConnectable((String) jsonObject.get("ip"), Integer.parseInt((String) jsonObject.get("port")))){
                AlertBody alertBody = new AlertBody();
                alertBody.setType(MonitorTypeConstant.SOFTWARE);
                Map<String,String> info = new HashMap<>(4);
                info.put("ip", (String) jsonObject.get("ip"));
                info.put("port",(String) jsonObject.get("port"));
                info.put("name",serverSoftwareProfileDo.getSoftwareName());
                info.put("status","death");
                alertBody.setInfo(info);
                //告警并入库
                alertHandler.alert(alertBody);
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


}
