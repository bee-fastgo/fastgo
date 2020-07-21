package com.bee.team.fastgo.config.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ConfigProjectBo
 * @Description 项目配置
 * @Author xqx
 * @Date 2020/7/20 18:36
 * @Version 1.0
 **/
public interface ConfigProjectBo<T> {
    /**
     * @return map 返回项目的配置信息
     * @Author xqx
     * @Description 添加项目配置信息
     * @Date 18:38 2020/7/20
     * @Param map 添加的参数
     **/
    Map<String, Object> insertProject(Map<String, Map<String, Object>> map);

    /**
     * @return DeleteResult
     * @Author xqx
     * @Description 删除一个项目
     * @Date 18:40 2020/7/20
     * @Param map 条件 不能为空
     **/
    DeleteResult removeOneProject(Map<String, Object> map);

    /**
     * @return DeleteResult
     * @Author xqx
     * @Description 删除所有的项目
     * @Date 18:42 2020/7/20
     * @Param
     **/
    DeleteResult removeAllProjects();

    /**
     * @return UpdateResult
     * @Author xqx
     * @Description 修改配置
     * @Date 18:43 2020/7/20
     * @Param queryMap 条件
     * @Param updateMap 修改键值对
     **/
    UpdateResult updateOneProject(Map<String, Object> queryMap, Map<String, Object> updateMap);

    /**
     * @return map
     * @Author xqx
     * @Description 根据条件获取一个项目的配置信息
     * @Date 18:48 2020/7/20
     * @Param map 查询条件参数
     * @Param t 返回的数据类型
     **/
    T getOneProjectConfigInfo(Map<String, Object> map, Class<T> t);

    /**
     * @return JSON格式的配置信息
     * @Author xqx
     * @Description
     * @Date 15:22 2020/7/21
     * @Param map 查询条件
     **/
    String getOneProjectConfigToJSON(Map<String, Object> map);

    /**
     * @return list
     * @Author xqx
     * @Description 根据条件获取配置信息列表
     * @Date 9:15 2020/7/21
     * @Param map 参数信息
     * @Param t 返回的数据类型
     **/
    List<T> getProjectConfigList(Map<String, Object> map, Class<T> t);

    /**
     * @return list
     * @Author xqx
     * @Description 获取所有的配置信息
     * @Date 9:16 2020/7/21
     * @Param t 返回的数据类型
     **/
    List<T> getAllProjectConfigList(Class<T> t);

    /**
     * @return Long
     * @Author xqx
     * @Description 获取所有项目的数量
     * @Date 9:17 2020/7/21
     **/
    Long countAllProjectConfig();

    /**
     * @return long
     * @Author xqx
     * @Description
     * @Date 9:18 2020/7/21
     * @Param map 参数类
     **/
    Long countProjectByCondition(Map<String, Object> map);
}
