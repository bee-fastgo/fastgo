package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.exception.sever.ScriptException;
import com.bee.team.fastgo.service.api.server.dto.req.ReqExecScriptDTO;
import com.bee.team.fastgo.service.api.server.ScriptApi;
import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.service.server.ServerScriptBo;
import com.spring.simple.development.support.exception.GlobalException;
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
    public String execScript(ReqExecScriptDTO reqExecScriptDTO) {
        checkParam(reqExecScriptDTO);
        String softwareName = reqExecScriptDTO.getSoftwareName();
        String version = reqExecScriptDTO.getVersion();
        String type = reqExecScriptDTO.getType();
        String ip = reqExecScriptDTO.getIp();
        //获取脚本
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version,type);

        // TODO 脚本参数问题待解决 动态参数?
        String param = StringUtils.join(Arrays.asList(softwareName, version, reqExecScriptDTO.getSoftwareDownloadUrl()), ",");
        //执行
        return SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, serverScriptDo.getScript(), param, -1, ip);
    }




    /**
     * 参数校验
     * @param reqExecScriptDTO 待校验的参数
     * @author jgz
     * @date 2020/7/25
     * @desc
     */
    private void checkParam(ReqExecScriptDTO reqExecScriptDTO){
        if(StringUtils.isEmpty(reqExecScriptDTO.getIp())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"服务器IP为空");
        }
        if(StringUtils.isEmpty(reqExecScriptDTO.getSoftwareName())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件名为空");
        }
        if(StringUtils.isEmpty(reqExecScriptDTO.getVersion())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件版本号为空");
        }
        if(StringUtils.isEmpty(reqExecScriptDTO.getType())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"脚本类型为空");
        }
    }

}
