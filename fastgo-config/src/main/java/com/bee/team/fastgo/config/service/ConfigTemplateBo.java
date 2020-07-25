package com.bee.team.fastgo.config.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author xqx
 * @date 2020/7/20
 * @desc 模板类
 **/
public interface ConfigTemplateBo<T> {
    /**
     * 添加模板信息
     *
     * @param map
     * @return {@link Map< String, Object>}
     * @author xqx
     * @date 2020/7/25
     * @desc 添加模板信息
     */
    Map<String, Object> insertTemplate(Map<String, Object> map);

    /**
     * 批量添加模板信息
     *
     * @param list
     * @return {@link Collection< Map< String, Object>>}
     * @author xqx
     * @date 2020/7/25
     * @desc 批量添加模板信息
     */
    Collection<Map<String, Object>> insertManyTemplateList(List<Map<String, Object>> list);

    /**
     * 修改模板信息
     *
     * @param conditionMap
     * @param setMap
     * @return {@link UpdateResult}
     * @author xqx
     * @date 2020/7/25
     * @desc 修改模板信息
     */
    UpdateResult updateTemplate(Map<String, Object> conditionMap, Map<String, Object> setMap);

    /**
     * 删除模板里面的一条数据(一个键值对)
     *
     * @param code
     * @param key
     * @return {@link UpdateResult}
     * @author xqx
     * @date 2020/7/25
     * @desc 删除模板里面的一条数据(一个键值对)
     */
    UpdateResult removeOneDataByCondition(String code, String key);

    /**
     * 删除模板信息
     *
     * @param map
     * @return {@link DeleteResult}
     * @author xqx
     * @date 2020/7/25
     * @desc 删除模板信息
     */
    DeleteResult removeTemplateByCondition(Map<String, Object> map);

    /**
     * 删除所有的模板数据
     *
     * @param
     * @return {@link DeleteResult}
     * @author xqx
     * @date 2020/7/25
     * @desc 删除所有的模板数据
     */
    DeleteResult removeAllTemplates();

    /**
     * 获取所有模板对象
     *
     * @param t
     * @return {@link List<T>}
     * @author xqx
     * @date 2020/7/25
     * @desc 获取所有模板对象
     */
    List<T> findAllTemplateList(Class<T> t);

    /**
     * 根据条件获取模板信息集合(模糊查询)
     *
     * @param map
     * @param t
     * @return {@link List<T>}
     * @author xqx
     * @date 2020/7/25
     * @desc 根据条件获取模板信息集合(模糊查询)
     */
    List<T> findTemplateListByCondition(Map<String, Object> map, Class<T> t);

    /**
     * 根据条件获取一条模板信息
     *
     * @param map
     * @param t
     * @return {@link T}
     * @author xqx
     * @date 2020/7/25
     * @desc 根据条件获取一条模板信息
     */
    T findTemplateByCondition(Map<String, Object> map, Class<T> t);

    /**
     * 获取所有的模板的数量
     *
     * @param
     * @return {@link Long}
     * @author xqx
     * @date 2020/7/25
     * @desc 获取所有的模板的数量
     */
    Long countAllTemplates();

    /**
     * 获取指定条件的模板的数量(模糊查询)
     *
     * @param map
     * @return {@link Long}
     * @author xqx
     * @date 2020/7/25
     * @desc 获取指定条件的模板的数量(模糊查询)
     */
    Long countTemplateByCondition(Map<String, Object> map);
}
