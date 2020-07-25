package com.bee.team.fastgo.exception.config;

import com.spring.simple.development.support.exception.GlobalResponseCode;

/**
 * @author xqx
 * @date 2020/7/23
 * @desc 模板异常类
 **/
public class TemplateException {
    public static final GlobalResponseCode INSERT_TEMPLATE_FAILED = new GlobalResponseCode(1100, "添加模板失败", "添加模板失败");
    public static final GlobalResponseCode UPDATE_TEMPLATE_FAILED = new GlobalResponseCode(1101, "修改模板失败", "修改模板失败");
    public static final GlobalResponseCode REMOVE_TEMPLATE_FAILED = new GlobalResponseCode(1102, "删除模板失败", "删除模板失败");
}
