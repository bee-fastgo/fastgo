package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.common.ScriptTypeConstant;
import com.bee.team.fastgo.exception.sever.ScriptException;
import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.service.api.server.ScriptApi;
import com.bee.team.fastgo.service.api.server.dto.req.ReqExecInstallScriptDTO;
import com.bee.team.fastgo.service.api.server.dto.req.ReqExecUnInstallScriptDTO;
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
    public String execInstallScript(ReqExecInstallScriptDTO reqExecInstallScriptDTO) {
        if(StringUtils.isEmpty(reqExecInstallScriptDTO.getIp())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"服务器IP为空");
        }
        if(StringUtils.isEmpty(reqExecInstallScriptDTO.getSoftwareName())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件名为空");
        }
        if(StringUtils.isEmpty(reqExecInstallScriptDTO.getVersion())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件版本号为空");
        }

        String softwareName = reqExecInstallScriptDTO.getSoftwareName();
        String version = reqExecInstallScriptDTO.getVersion();
        String ip = reqExecInstallScriptDTO.getIp();
        //获取脚本
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version, ScriptTypeConstant.INSTALL);

        // TODO 脚本参数问题待解决 动态参数?
        String param = StringUtils.join(Arrays.asList(softwareName, version, reqExecInstallScriptDTO.getSoftwareDownloadUrl()), ",");
        return SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, serverScriptDo.getScript(), param, -1, ip);
    }

    @Override
    public String execUninstallScript(ReqExecUnInstallScriptDTO reqExecUnInstallScriptDTO) {
        if(StringUtils.isEmpty(reqExecUnInstallScriptDTO.getIp())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"服务器IP为空");
        }
        if(StringUtils.isEmpty(reqExecUnInstallScriptDTO.getSoftwareName())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件名为空");
        }
        if(StringUtils.isEmpty(reqExecUnInstallScriptDTO.getVersion())) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL,"软件版本号为空");
        }

        String softwareName = reqExecUnInstallScriptDTO.getSoftwareName();
        String version = reqExecUnInstallScriptDTO.getVersion();
        String ip = reqExecUnInstallScriptDTO.getIp();

        //获取脚本
        ServerScriptDo serverScriptDo = serverScriptBo.getScriptBySoftwareNameAndVersionAndType(softwareName,version, ScriptTypeConstant.UNINSTALL);

        String param = StringUtils.join(Arrays.asList(softwareName, version), ",");
        return SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, serverScriptDo.getScript(), param, -1, ip);
    }



}
