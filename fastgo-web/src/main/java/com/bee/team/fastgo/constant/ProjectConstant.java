package com.bee.team.fastgo.constant;

public class ProjectConstant {

    /**
     * 项目类型 1-前端项目  2-后台项目
     */
    public static final Integer PROJECT_TYPE1 = 1;
    public static final Integer PROJECT_TYPE2 = 2;

    /**
     * 项目状态：1.项目创建中，2.项目创建成功，3.项目部署中 4.项目部署完成，5-软件环境部署成功，6-运行环境部署成功,7-项目部署失败
     */
    public static final Integer PROJECT_STATUS1 = 1;
    public static final Integer PROJECT_STATUS2 = 2;
    public static final Integer PROJECT_STATUS3 = 3;
    public static final Integer PROJECT_STATUS4 = 4;
    public static final Integer PROJECT_STATUS5 = 5;
    public static final Integer PROJECT_STATUS6 = 6;
    public static final Integer PROJECT_STATUS7 = 7;

    /**
     * 项目分支： 默认（master）
     */
    public static final String PROJECT_BRANCH = "master";

    /**
     * 修改项目状态对象类型 1-运行环境，2-软件环境
     */
    public static final Integer OBJECT_TYPE1 = 1;
    public static final Integer OBJECT_TYPE2 = 2;

    /**
     * 前台项目模板
     */
    public static final String FRONT_PROJECT_TYPE1 = "backend-template";
    public static final String FRONT_PROJECT_TYPE2 = "h5-template";

    public static final Integer FRONT_PROJECT_TEMPLATE1 = 1;
    public static final Integer FRONT_PROJECT_TEMPLATE2 = 2;

    /**
     * 是否生成项目框架
     */
    public static final Integer IS_CONFIRM0 = 0;
    public static final Integer IS_CONFIRM1 = 1;

    /**
     * 自动部署开关： 0-关闭 1-开启
     */
    public static final Integer AUTO_DEPLOY0 = 0;
    public static final Integer AUTO_DEPLOY1 = 1;

    /**
     * 软件环境/运行环境： 0-新建 1-已存在
     */
    public static final Integer HAS_PROFILE0 = 0;
    public static final Integer HAS_PROFILE1 = 1;

}
