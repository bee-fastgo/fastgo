package com.bee.team.fastgo.server.core.route.strategy;


import com.bee.team.fastgo.server.core.route.ExecutorRouter;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.biz.model.TriggerParam;

import java.util.List;

/**
 * Created by luke on 17/3/10.
 */
public class ExecutorRouteLast extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(addressList.size()-1));
    }

}
