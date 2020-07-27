package com.bee.team.fastgo.hander;

import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;

/**
 * @author luke
 * @desc 任务推送
 * @date 2020-07-27
 **/
public interface JobPush {
    /**
     * 接受回调
     *
     * @param handleCallbackParam
     */
    void receiveMessage(HandleCallbackParam handleCallbackParam);
}
