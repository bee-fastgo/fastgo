package com.bee.team.fastgo.hander.event;

import com.bee.team.fastgo.hander.InitServer;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import org.springframework.context.ApplicationEvent;


/**
 * @Author luke
 * @Description 任务消息推送
 * @Date 9:22 2020/7/2 0002
 **/
public class InitServerEvent extends ApplicationEvent {

    public InitServerEvent(InitServer initServer) {
        super(initServer);
    }

}
