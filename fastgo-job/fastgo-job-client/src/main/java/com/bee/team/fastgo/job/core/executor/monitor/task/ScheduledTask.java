package com.bee.team.fastgo.job.core.executor.monitor.task;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.job.core.executor.monitor.entity.*;
import com.bee.team.fastgo.job.core.executor.monitor.util.SigarUtil;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.HttpClientUtils;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 任务
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTask {

    private static Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Value("${save_server_monitor_url}")
    private String url;

    /**
     * 60秒后执行，每隔60秒执行, 单位：ms。
     */
    @Scheduled(fixedDelay = 60 * 1000)
    public void minTask() {
        JSONObject jsonObject = new JSONObject();
        Date date = new Date();
        try {
            // 操作系统信息
            SystemInfo systemInfo = SigarUtil.os();
            logger.info(DateUtils.getCurrentTime() + "systemInfo = " + JSON.toJSON(systemInfo));
            // 文件系统信息
            // List<DeskState> deskStateList = SigarUtil.file();
            // cpu信息
            CpuState cpuState = SigarUtil.cpu();
            logger.info(DateUtils.getCurrentTime() + "cpuState = " + JSON.toJSON(cpuState));
            // 内存信息
            MemoryState memState = SigarUtil.memory();
            logger.info(DateUtils.getCurrentTime() + "memState = " + JSON.toJSON(memState));

            // 网络流量信息
            NetIoState netIoState = SigarUtil.net();
            logger.info(DateUtils.getCurrentTime() + "netIoState = " + JSON.toJSON(netIoState));

            // 系统负载信息
            SysLoadState sysLoadState = SigarUtil.getLoadState();

            if (cpuState != null) {
                cpuState.setGmtCreate(date);
                jsonObject.put("cpuState", cpuState);
            }
            if (memState != null) {
                memState.setGmtCreate(date);
                jsonObject.put("memState", memState);
            }
            if (netIoState != null) {
                netIoState.setGmtCreate(date);
                jsonObject.put("netIoState", netIoState);
            }
            if (sysLoadState != null) {
                sysLoadState.setGmtCreate(date);
                jsonObject.put("sysLoadState", sysLoadState);
            }
            if (systemInfo != null) {
                jsonObject.put("systemInfo", systemInfo);
            }
        } catch (SigarException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "application/json");
            headers.put("Accept-Charset", "utf-8");
            String json = JSON.toJSONString(jsonObject);
            logger.info(DateUtils.getCurrentTime() + "req json" + json);
            String result = HttpClientUtils.doPostByJson(url, headers, json);
            logger.info(DateUtils.getCurrentTime() + "result" + result);
        }
    }

}
