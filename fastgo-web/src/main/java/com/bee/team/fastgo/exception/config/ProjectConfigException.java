package com.bee.team.fastgo.exception.config;

import com.spring.simple.development.support.exception.GlobalResponseCode;

/**
 * @ClassName ProjectConfigException
 * @Description 项目配置异常类
 * @Author xqx
 * @Date 2020/7/23 15:02
 * @Version 1.0
 **/
public class ProjectConfigException {
    public static final GlobalResponseCode UPDATE_PROJECT_FAILED = new GlobalResponseCode(1000, "修改项目失败", "修改项目失败");
    public static final GlobalResponseCode REMOVE_PROJECT_FAILED = new GlobalResponseCode(1001, "删除项目失败", "删除项目失败");
}
