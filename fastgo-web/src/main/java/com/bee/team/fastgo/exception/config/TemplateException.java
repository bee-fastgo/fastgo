package com.bee.team.fastgo.exception.config;

import com.spring.simple.development.support.exception.GlobalResponseCode;

/**
 * @ClassName TemplateException
 * @Description 模板异常类
 * @Author xqx
 * @Date 2020/7/23 15:02
 * @Version 1.0
 **/
public class TemplateException {
    public static final GlobalResponseCode INSERT_TEMPLATE_FAILED = new GlobalResponseCode(1100, "添加模板失败", "%s");
    public static final GlobalResponseCode UPDATE_TEMPLATE_FAILED = new GlobalResponseCode(1101, "修改模板失败", "%s");
    public static final GlobalResponseCode REMOVE_TEMPLATE_FAILED = new GlobalResponseCode(1102, "删除模板失败", "%s");
}
