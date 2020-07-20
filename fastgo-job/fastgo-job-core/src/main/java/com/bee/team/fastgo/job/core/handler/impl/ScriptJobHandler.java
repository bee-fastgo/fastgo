package com.bee.team.fastgo.job.core.handler.impl;

import com.bee.team.fastgo.job.core.handler.IJobHandler;
import com.bee.team.fastgo.job.core.biz.model.ReturnT;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.job.core.log.SimpleJobFileAppender;
import com.bee.team.fastgo.job.core.log.SimpleJobLogger;
import com.bee.team.fastgo.job.core.util.ScriptUtil;
import com.bee.team.fastgo.job.core.util.ShardingUtil;

import java.io.File;

/**
 * Created by luke on 17/4/27.
 */
public class ScriptJobHandler extends IJobHandler {

    private int jobId;
    private long glueUpdatetime;
    private String gluesource;
    private GlueTypeEnum glueType;

    public ScriptJobHandler(int jobId, long glueUpdatetime, String gluesource, GlueTypeEnum glueType) {
        this.jobId = jobId;
        this.glueUpdatetime = glueUpdatetime;
        this.gluesource = gluesource;
        this.glueType = glueType;

        // clean old script file
        File glueSrcPath = new File(SimpleJobFileAppender.getGlueSrcPath());
        if (glueSrcPath.exists()) {
            File[] glueSrcFileList = glueSrcPath.listFiles();
            if (glueSrcFileList != null && glueSrcFileList.length > 0) {
                for (File glueSrcFileItem : glueSrcFileList) {
                    if (glueSrcFileItem.getName().startsWith(String.valueOf(jobId) + "_")) {
                        glueSrcFileItem.delete();
                    }
                }
            }
        }

    }

    public long getGlueUpdatetime() {
        return glueUpdatetime;
    }

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        if (!glueType.isScript()) {
            return new ReturnT<String>(IJobHandler.FAIL.getCode(), "glueType[" + glueType + "] invalid.");
        }

        // cmd
        String cmd = glueType.getCmd();

        // make script file
        String scriptFileName = SimpleJobFileAppender.getGlueSrcPath()
                .concat(File.separator)
                .concat(String.valueOf(jobId))
                .concat("_")
                .concat(String.valueOf(glueUpdatetime))
                .concat(glueType.getSuffix());
        File scriptFile = new File(scriptFileName);
        if (!scriptFile.exists()) {
            ScriptUtil.markScriptFile(scriptFileName, gluesource);
        }

        // log file
        String logFileName = SimpleJobFileAppender.contextHolder.get();

        // script params：
        String shardingVo = ShardingUtil.getShardingVo();
        String[] scriptParams;
        if (shardingVo != null) {
            String[] params = shardingVo.split(",");
            scriptParams = new String[params.length + 1];
            scriptParams[0] = param;
            for (int i = 0; i < params.length; i++) {
                scriptParams[i + 1] = params[i];
            }
        } else {
            scriptParams = new String[1];
            scriptParams[0] = param;
        }

        // invoke
        SimpleJobLogger.log("----------- script file:" + scriptFileName + " -----------");
        int exitValue = ScriptUtil.execToFile(cmd, scriptFileName, logFileName, scriptParams);

        if (exitValue == 0) {
            return IJobHandler.SUCCESS;
        } else {
            return new ReturnT<String>(IJobHandler.FAIL.getCode(), "script exit value(" + exitValue + ") is failed");
        }

    }

}
