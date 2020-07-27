package com.bee.team.fastgo.hander;

import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;

/**
 * @author luke
 * @desc
 * @date 2020-07-27
 **/
public class JobPushImpl implements JobPush {
    @Override
    public void receiveMessage(HandleCallbackParam handleCallbackParam) {
        System.out.println(handleCallbackParam);
    }
}
