package com.bee.team.fastgo.server.core.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.biz.model.TriggerParam;

import java.util.List;

/**
 * Created by luke on 17/3/10.
 */
public abstract class ExecutorRouter {
    protected static Logger logger = LoggerFactory.getLogger(ExecutorRouter.class);

    /**
     * route address
     *
     * @param addressList
     * @return  ReturnT.content=address
     */
    public abstract ReturnT<String> route(TriggerParam triggerParam, List<String> addressList);

}
