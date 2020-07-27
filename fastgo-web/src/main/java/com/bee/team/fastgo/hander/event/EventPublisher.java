package com.bee.team.fastgo.hander.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @Author luke
 * @Description 事件发布bean
 * @Date 19:11 2020/7/3 0003
 **/
@Component
public class EventPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 发布事件
     * @param event
     */
    public void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}