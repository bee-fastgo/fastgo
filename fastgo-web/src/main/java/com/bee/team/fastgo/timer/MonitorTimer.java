package com.bee.team.fastgo.timer;

import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.model.ServerRunProfileDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.service.server.ServerRunProfileBo;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jgz
 * @date 2020/8/4
 * @desc 监控定时调度
 **/
@Component
@EnableScheduling
public class MonitorTimer {


    @Autowired
    private ServerRunProfileBo serverRunProfileBo;

    @Autowired
    private ServerSoftwareProfileBo serverSoftwareProfileBo;

    /**
     * 每分钟执行一次任务调度
     * @author jgz
     * @date 2020/8/4
     * @desc TODO
     */
//    @Scheduled(cron = "0 */1 * * * ?")
    public void execute(){
        List<ServerSoftwareProfileDo> serverSoftwareProfileDoList = serverSoftwareProfileBo.getAll();
        serverSoftwareProfileDoList.forEach(serverSoftwareProfileDo -> {
            SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, "#!/bin/bash\n" +
                    "\n" +
                    "if [[  -z $(lsof -i:6379) ]];then\n" +
                    "        echo \"{\"ip\":\"172.0.0.1\",\"port\":\"6379\",\"name\":\"redis\",\"status\":\"died\"}\"\n" +
                    "else\n" +
                    "        echo \"{\"ip\":\"172.0.0.1\",\"port\":\"6379\",\"name\":\"redis\",\"status\":\"survive\"}\"\n" +
                    "fi", null, -1, serverSoftwareProfileDo.getServerIp());
        });
        List<ServerRunProfileDo> serverRunProfileDoList = serverRunProfileBo.getListServerRunProfileDo();

    }





}
