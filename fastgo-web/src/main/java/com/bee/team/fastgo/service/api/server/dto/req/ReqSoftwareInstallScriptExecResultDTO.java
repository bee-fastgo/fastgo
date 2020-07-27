package com.bee.team.fastgo.service.api.server.dto.req;

import java.io.Serializable;

/**
 * @author jgz
 * @date 2020/7/27
 * @desc 安装软件脚本执行结果实体
 **/
public class ReqSoftwareInstallScriptExecResultDTO implements Serializable {

    /**
     * 查询id
     */
    private String selectId;

    /**
     * 执行结果 (0.失败 1.成功)
     */
    private String result;

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
