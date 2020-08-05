package com.bee.team.fastgo.hander.alert;

import com.alibaba.fastjson.JSON;
import com.bee.team.fastgo.common.MonitorTypeConstant;
import com.bee.team.fastgo.hander.event.AlertEvent;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import com.spring.simple.development.support.utils.DateUtils;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jgz
 * @date 2020/8/5
 * @desc 告警时间处理
 **/
@EnableAsync
@Component
public class AlertListener {

    @Async
    @EventListener
    public void alertHandle(AlertEvent alertEvent){
        AlertModel alertModel = (AlertModel) alertEvent.getSource();
        String msg = null;
        if (MonitorTypeConstant.SOFTWARE.equals(alertModel.getType())){
            msg = " > **应用名: " + alertModel.getKeyword() + "**        \n"+
                    "   **类型: " + ":" + alertModel.getType() + "**        \n" +
                    "   **检测时间: " + DateUtils.getStrMMDDYYYYFormateDate(new Date()) + "**        \n" +
                    "   **规则: " + alertModel.getRule() + "**        \n" +
                    "   **详情:**        \n" +
                    " <font color=#FF0000 > name:" + alertModel.getInfo().get("name") + "</font>        \n" +
                    " <font color=#FF0000 > ip:" + alertModel.getInfo().get("ip") + "</font>        \n" +
                    " <font color=#FF0000 > port:" + alertModel.getInfo().get("port") + "</font>        \n" +
                    " <font color=#FF0000 > status:" + alertModel.getInfo().get("status") + "</font>        \n";

        } else if (MonitorTypeConstant.PROJECT.equals(alertModel.getType())){
           msg = " > **应用名: " + alertModel.getKeyword() + "**        \n"+
                   "   **类型: " + ":" + alertModel.getType() + "**        \n" +
                   "   **检测时间: " + DateUtils.getStrMMDDYYYYFormateDate(new Date()) + "**        \n" +
                   "   **规则: " + alertModel.getRule() + "**        \n" +
                   "   **详情:**        \n" +
                   " <font color=#FF0000 > name:" + alertModel.getInfo().get("name") + "</font>        \n" +
                   " <font color=#FF0000 > ip:" + alertModel.getInfo().get("ip") + "</font>        \n" +
                   " <font color=#FF0000 > port:" + alertModel.getInfo().get("port") + "</font>        \n" +
                   " <font color=#FF0000 > status:" + alertModel.getInfo().get("status") + "</font>        \n";
        }
        else if (MonitorTypeConstant.SERVER.equals(alertModel.getType())){
            msg = " > **应用名: " + alertModel.getKeyword() + "**        \n"+
                    "   **类型: " + ":" + alertModel.getType() + "**        \n" +
                    "   **检测时间: " + DateUtils.getStrMMDDYYYYFormateDate(new Date()) + "**        \n" +
                    "   **规则: " + alertModel.getRule() + "**        \n" +
                    "   **详情:**        \n" +
                    " <font color=#FF0000 > ip:" + alertModel.getInfo().get("ip") + "</font>        \n" +
                    " <font color=#FF0000 > cpu:" + alertModel.getInfo().get("cpu") + "</font>        \n" +
                    " <font color=#FF0000 > mem:" + alertModel.getInfo().get("mem") + "</font>        \n";
        }
        sendMarkdownMsg(alertModel.getToken(),alertModel.getKeyword(),msg);
    }


    public static void sendMarkdownMsg(String accessToken, String keywords, String msg)  {
        if(StringUtils.isEmpty(msg)){
            return;
        }
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=" + accessToken);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(keywords);

        markdown.setText(msg);
        request.setMarkdown(markdown);
        OapiRobotSendResponse response = null;
        try {
            response = client.execute(request);
            if (!response.isSuccess()) {
                throw new GlobalException(ResponseCode.SERVICE_FAILED, "钉钉告警发送失败");
            }
        } catch (ApiException e) {
            throw new GlobalException(ResponseCode.SERVICE_FAILED, "告警api调用失败");
        }


    }

}
