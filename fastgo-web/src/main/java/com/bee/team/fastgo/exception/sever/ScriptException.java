package com.bee.team.fastgo.exception.sever;

import com.spring.simple.development.support.exception.GlobalResponseCode;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/24 10:17
 * @ClassName ScriptException
 * @Description 脚本相关自定义异常
 **/
public class ScriptException {

    /**
     * 脚本异常
     */
    public static final GlobalResponseCode SCRIPT_ABNORMAL = new GlobalResponseCode(1301,"script abnormal","%s");


    /**
     * 脚本异常
     */
    public static final GlobalResponseCode ENV_ABNORMAL = new GlobalResponseCode(1301,"env abnormal","%s");
}
