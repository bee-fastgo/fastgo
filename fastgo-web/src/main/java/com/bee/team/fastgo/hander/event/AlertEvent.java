package com.bee.team.fastgo.hander.event;

import com.bee.team.fastgo.hander.alert.AlertModel;
import org.springframework.context.ApplicationEvent;

/**
 * @author jgz
 * @date 2020/8/5
 * @desc 告警事件
 **/
public class AlertEvent extends ApplicationEvent {

    public AlertEvent(AlertModel alertModel) {
        super(alertModel);
    }
}
