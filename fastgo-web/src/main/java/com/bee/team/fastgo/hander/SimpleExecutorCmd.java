package com.bee.team.fastgo.hander;

import com.bee.team.fastgo.job.core.enums.ExecutorBlockStrategyEnum;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.model.ServerExecutorLogDo;
import com.bee.team.fastgo.server.core.model.SimpleJobAddress;
import com.bee.team.fastgo.server.core.model.SimpleJobInfo;
import com.bee.team.fastgo.server.core.model.SimpleJobLog;
import com.bee.team.fastgo.server.core.trigger.SimpleJobTrigger;
import com.bee.team.fastgo.server.core.trigger.TriggerTypeEnum;
import com.bee.team.fastgo.service.server.ServerExecutorLogBo;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.utils.PrimaryKeyGenerator;
import com.spring.simple.development.support.utils.RandomUtil;
import com.spring.simple.development.support.utils.RandomUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 执行脚本
 * @author: luke
 * @time: 2020/7/20 0020 15:40
 */
@Component
public class SimpleExecutorCmd implements InitializingBean {

    private static SimpleExecutorCmd simpleExecutorCmd = null;

    public SimpleExecutorCmd() {

    }

    @Resource
    private BaseSupport baseSupport;
    @Resource
    private ServerExecutorLogBo serverExecutorLogBo;

    public static String executorCmd(GlueTypeEnum glueTypeEnum, String scriptSource, String params, int executorTimeout, String ip) {
        // 构建执行脚本
        SimpleJobInfo simpleJobInfo = getSimpleJobInfo(scriptSource, params, executorTimeout, glueTypeEnum);

        // 执行的服务器
        SimpleJobAddress simpleJobAddress = new SimpleJobAddress();
        simpleJobAddress.setAddressType(0);
        simpleJobAddress.setAddressList(ip);

        ServerExecutorLogDo beforeServerExecutorLogDo = new ServerExecutorLogDo();
        beforeServerExecutorLogDo.setId(simpleJobInfo.getId());
        beforeServerExecutorLogDo.setExecutorAddress(ip);
        SimpleExecutorCmd.simpleExecutorCmd.serverExecutorLogBo.addServerExecutorLogDo(beforeServerExecutorLogDo);

        List<SimpleJobLog> triggerResult = SimpleJobTrigger.trigger(simpleJobInfo, TriggerTypeEnum.API, simpleJobAddress);
        ServerExecutorLogDo serverExecutorLogDo = SimpleExecutorCmd.simpleExecutorCmd.baseSupport.objectCopy(triggerResult.get(0), ServerExecutorLogDo.class);
        serverExecutorLogDo.setStatus(CommonConstant.CODE0);
        SimpleExecutorCmd.simpleExecutorCmd.serverExecutorLogBo.modifyServerExecutorLogDo(serverExecutorLogDo);
        return String.valueOf(serverExecutorLogDo.getId());
    }

    @Override
    public void afterPropertiesSet() {
        simpleExecutorCmd = this;
    }

    /**
     * @return com.bee.team.fastgo.server.core.model.SimpleJobInfo
     * @Author luke
     * @Description 构建jobInfo
     * @Date 16:23 2020/7/20 0020
     * @Param [scriptSource, params, executorTimeout]
     **/
    private static SimpleJobInfo getSimpleJobInfo(String scriptSource, String params, int executorTimeout, GlueTypeEnum glueTypeEnum) {
        SimpleJobInfo simpleJobInfo = new SimpleJobInfo();
        simpleJobInfo.setId(PrimaryKeyGenerator.getInstance().nextId());
        simpleJobInfo.setThreadId(Long.valueOf(RandomUtil.getRandomInt(18)));
        simpleJobInfo.setAddTime(new Date());
        simpleJobInfo.setExecutorHandler(null);
        simpleJobInfo.setExecutorParam(params);
        simpleJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.COVER_EARLY.name());
        simpleJobInfo.setExecutorTimeout(executorTimeout);
        simpleJobInfo.setExecutorFailRetryCount(-1);
        simpleJobInfo.setGlueType(glueTypeEnum.name());
        simpleJobInfo.setGlueSource(scriptSource);
        return simpleJobInfo;
    }
}
