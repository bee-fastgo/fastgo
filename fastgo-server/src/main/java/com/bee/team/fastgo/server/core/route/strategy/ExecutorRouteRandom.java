package com.bee.team.fastgo.server.core.route.strategy;


import com.bee.team.fastgo.server.core.route.ExecutorRouter;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.biz.model.TriggerParam;

import java.util.List;
import java.util.Random;

/**
 * Created by luke on 17/3/10.
 */
public class ExecutorRouteRandom extends ExecutorRouter {

    private static Random localRandom = new Random();

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        String address = addressList.get(localRandom.nextInt(addressList.size()));
        return new ReturnT<String>(address);
    }

}
