package com.bee.team.fastgo.job.core.biz.client;

import com.bee.team.fastgo.job.core.biz.AdminBiz;
import com.bee.team.fastgo.job.core.biz.model.RegistryParam;
import com.bee.team.fastgo.job.core.util.SimpleJobRemotingUtil;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;

import java.util.List;

/**
 * admin api test
 *
 * @author luke 2017-07-28 22:14:52
 */
public class AdminBizClient implements AdminBiz {

    public AdminBizClient() {
    }
    public AdminBizClient(String addressUrl, String accessToken) {
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
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return SimpleJobRemotingUtil.postBody(addressUrl+"api/callback", accessToken, timeout, callbackParamList, String.class);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return SimpleJobRemotingUtil.postBody(addressUrl + "api/registry", accessToken, timeout, registryParam, String.class);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return SimpleJobRemotingUtil.postBody(addressUrl + "api/registryRemove", accessToken, timeout, registryParam, String.class);
    }

}
