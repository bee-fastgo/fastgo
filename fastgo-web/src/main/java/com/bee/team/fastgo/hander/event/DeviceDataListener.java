package com.bee.team.fastgo.hander.event;

import com.bee.team.fastgo.hander.JobHandler;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @Author luke
 * @Description 任务日志push事件
 * @Date 19:11 2020/7/3 0003
 **/
@EnableAsync
@Component
public class DeviceDataListener {

    @Async
    @EventListener
    public void jobLogPushEventHandle(JobLogPushEvent jobLogPushEvent) {
        JobHandler.push((HandleCallbackParam) jobLogPushEvent.getSource());
    }
}
