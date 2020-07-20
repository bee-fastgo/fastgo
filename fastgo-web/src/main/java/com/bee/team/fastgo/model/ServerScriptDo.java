package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_server_script
 *
 * @author jgz
 * @date   2020/07/20
 */
@Data
public class ServerScriptDo extends LavaDo {
    /**
     * 脚本key
     */
    private String scriptkey;

    /**
     * 脚本名
     */
    private String scriptname;

    /**
     * 字典名
     */
    private String dictionaryname;

    /**
     * 版本
     */
    private String version;

    /**
     * 类型 
1.安装
2.修改
3.重启
4.卸载
     */
    private String type;

    /**
     * 脚本
     */
    private String script;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.bee.team.fastgo.service.server.ServerScriptBo";
    }
}