package com.bee.team.fastgo.config.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;
import java.util.Map;

/**
 * @author xqx
 * @date 2020/7/25
 * @desc 项目配置
 **/
public interface ConfigProjectBo<T> {
    /**
     * 添加项目配置信息
     *
     * @param map
     * @return {@link String}
     * @author xqx
     * @date 2020/7/25
     * @desc 添加项目配置信息
     */
    String insertProject(Map<String, Map<String, Object>> map);

    /**
     * 删除一个项目
     *
     * @param map
     * @return {@link DeleteResult}
     * @author xqx
     * @date 2020/7/25
     * @desc 删除一个项目
     */
    DeleteResult removeOneProject(Map<String, Object> map);

    /**
     * 删除所有的项目
     *
     * @param
     * @return {@link DeleteResult}
     * @author xqx
     * @date 2020/7/25
     * @desc 删除所有的项目
     */
    DeleteResult removeAllProjects();

    /**
     * 删除项目里面的一条数据(一个键值对)
     *
     * @param code
     * @param key
     * @return {@link UpdateResult}
     * @author xqx
     * @date 2020/7/25
     * @desc 删除项目里面的一条数据(一个键值对)
     */
    UpdateResult removeOneDataByCondition(String code, String key);

    /**
     * 修改配置
     *
     * @param queryMap
     * @param updateMap
     * @return {@link UpdateResult}
     * @author xqx
     * @date 2020/7/25
     * @desc 修改配置
     */
    UpdateResult updateOneProject(Map<String, Object> queryMap, Map<String, Object> updateMap);

    /**
     * 根据条件获取一个项目的配置信息
     *
     * @param map
     * @param t
     * @return {@link T}
     * @author xqx
     * @date 2020/7/25
     * @desc 根据条件获取一个项目的配置信息
     */
    T getOneProjectConfigInfo(Map<String, Object> map, Class<T> t);

    /**
     * JSON格式的配置信息
     *
     * @param map
     * @return {@link String}
     * @author xqx
     * @date 2020/7/25
     * @desc JSON格式的配置信息
     */
    String getOneProjectConfigToJSON(Map<String, Object> map);

    /**
     * 根据条件获取配置信息列表(模糊查询)
     *
     * @param map
     * @param t
     * @return {@link List<T>}
     * @author xqx
     * @date 2020/7/25
     * @desc 根据条件获取配置信息列表(模糊查询)
     */
    List<T> getProjectConfigList(Map<String, Object> map, Class<T> t);

    /**
     * 获取所有的配置信息
     *
     * @param t
     * @return {@link List<T>}
     * @author xqx
     * @date 2020/7/25
     * @desc 获取所有的配置信息
     */
    List<T> getAllProjectConfigList(Class<T> t);

    /**
     * 获取所有项目的数量
     *
     * @param
     * @return {@link Long}
     * @author xqx
     * @date 2020/7/25
     * @desc 获取所有项目的数量
     */
    Long countAllProjectConfig();

    /**
     * 模糊查询项目数量
     *
     * @param map
     * @return {@link Long}
     * @author xqx
     * @date 2020/7/25
     * @desc 模糊查询项目数量
     */
    Long countProjectByCondition(Map<String, Object> map);
}
