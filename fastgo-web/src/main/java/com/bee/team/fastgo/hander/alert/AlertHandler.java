package com.bee.team.fastgo.hander.alert;

import com.alibaba.fastjson.JSON;
import com.bee.team.fastgo.hander.event.AlertEvent;
import com.bee.team.fastgo.hander.event.EventPublisher;
import com.bee.team.fastgo.model.AlertInfoDo;
import com.bee.team.fastgo.model.StrategyDo;
import com.bee.team.fastgo.service.server.AlertInfoBo;
import com.bee.team.fastgo.service.server.StrategyBo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jgz
 * @date 2020/8/5
 * @desc 告警处理
 **/
@Component
public class AlertHandler {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private StrategyBo strategyBo;

    @Autowired
    private AlertInfoBo alertInfoBo;


    /**
     * 告警
      * @param alertBody
     * @return
     * @author jgz
     * @date 2020/8/5
     * @desc
     */
    public void alert(AlertBody alertBody) {
        AlertInfoDo alertInfoDo = new  AlertInfoDo();
        alertInfoDo.setType(alertBody.getType());
        alertInfoDo.setInfo(JSON.toJSONString(alertBody.getInfo()));
        alertInfoBo.insertAlertInfo(alertInfoDo);

        // 异步告警
        AlertModel alertModel = baseSupport.objectCopy(alertBody, AlertModel.class);
        StrategyDo strategyDo = strategyBo.selectByTypeAndRule(alertModel.getType(),"default");
        if(strategyDo != null){
            alertModel.setRule(strategyDo.getRule());
            alertModel.setKeyword(strategyDo.getKeyword());
            alertModel.setToken(strategyDo.getToken());
            alertModel.setUrl(strategyDo.getUrl());
            eventPublisher.publishEvent(new AlertEvent(alertModel));
        }
    }
}
