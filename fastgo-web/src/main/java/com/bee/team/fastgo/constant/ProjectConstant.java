package com.bee.team.fastgo.constant;

public class ProjectConstant {

    /**
     * 项目类型 1-前端项目  2-后台项目
     */
    public static final Integer PROJECT_TYPE1 = 1;
    public static final Integer PROJECT_TYPE2 = 2;

    /**
     * 项目状态：1.项目创建中，2.项目创建成功，3.项目部署中 4.项目部署完成，5-软件环境部署成功，6-运行环境部署成功
     */
    public static final Integer PROJECT_STATUS1 = 1;
    public static final Integer PROJECT_STATUS2 = 2;
    public static final Integer PROJECT_STATUS3 = 3;
    public static final Integer PROJECT_STATUS4 = 4;
    public static final Integer PROJECT_STATUS5 = 5;
    public static final Integer PROJECT_STATUS6 = 6;

    /**
     * 项目分支： 默认（master）
     */
    public static final String PROJECT_BRANCH = "master";

    /**
     * 修改项目状态对象类型 1-运行环境，2-软件环境
     */
    public static final Integer OBJECT_TYPE1 = 1;
    public static final Integer OBJECT_TYPE2 = 2;

}
