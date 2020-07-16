package com.bee.team.fastgo.job.core.biz.client;

import com.bee.team.fastgo.job.core.biz.ExecutorBiz;
import com.bee.team.fastgo.job.core.biz.model.*;
import com.bee.team.fastgo.job.core.util.SimpleJobRemotingUtil;

/**
 * admin api test
 *
 * @author luke 2017-07-28 22:14:52
 */
public class ExecutorBizClient implements ExecutorBiz {

    public ExecutorBizClient() {
    }
    public ExecutorBizClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    private String addressUrl ;
    private String accessToken;
    private int timeout = 3;


    @Override
    public ReturnT<String> beat() {
        return SimpleJobRemotingUtil.postBody(addressUrl+"beat", accessToken, timeout, null, String.class);
    }

    @Override
    public ReturnT<String> idleBeat(IdleBeatParam idleBeatParam){
        return SimpleJobRemotingUtil.postBody(addressUrl+"idleBeat", accessToken, timeout, idleBeatParam, String.class);
    }

    @Override
    public ReturnT<String> run(TriggerParam triggerParam) {
        return SimpleJobRemotingUtil.postBody(addressUrl + "run", accessToken, timeout, triggerParam, String.class);
    }

    @Override
    public ReturnT<String> kill(KillParam killParam) {
        return SimpleJobRemotingUtil.postBody(addressUrl + "kill", accessToken, timeout, killParam, String.class);
    }

    @Override
    public ReturnT<LogResult> log(LogParam logParam) {
        return SimpleJobRemotingUtil.postBody(addressUrl + "log", accessToken, timeout, logParam, LogResult.class);
    }

}
