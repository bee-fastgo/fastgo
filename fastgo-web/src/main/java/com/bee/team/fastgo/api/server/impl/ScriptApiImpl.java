package com.bee.team.fastgo.api.server.impl;

import com.bee.team.fastgo.api.server.ScriptApi;
import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.service.server.ServerScriptBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/25 13:43
 * @ClassName ScriptApiImpl
 * @Description 脚本相关接口实现
 **/
@Service
public class ScriptApiImpl implements ScriptApi {

    @Autowired
    private ServerScriptBo serverScriptBo;

    @Override
    public String execScript(String softwareName, String version, String type, String ip) {

        //获取脚本
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version,type);
        // TODO 获取软件下载地址

        //参数
        String param = StringUtils.join(Arrays.asList(softwareName, version, type), ",");
        //执行
        return SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, serverScriptDo.getScript(), param, -1, ip);
    }

}
