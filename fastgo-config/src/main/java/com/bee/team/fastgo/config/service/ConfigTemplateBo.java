package com.bee.team.fastgo.config.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConfigTemplateBo
 * @Description 模板类
 * @Author xqx
 * @Date 2020/7/20 15:05
 * @Version 1.0
 **/
public interface ConfigTemplateBo<T> {
    /**
     * @return map
     * @Author xqx
     * @Description 添加模板信息
     * @Date 15:18 2020/7/20
     * @Param map 添加的键值对
     **/
    Map<String, Object> insertTemplate(Map<String, Object> map);

    /**
     * @return
     * @Author xqx
     * @Description 批量添加模板信息
     * @Date 17:01 2020/7/20
     * @Param
     **/
    Collection<Map<String, Object>> insertManyTemplateList(List<Map<String, Object>> list);

    /**
     * @return UpdateRequest
     * @Author xqx
     * @Description 修改模板信息
     * @Date 15:26 2020/7/20
     * @Param setMap 要修改的键值对
     * @Param conditionMap 条件判断
     **/
    UpdateResult updateTemplate(Map<String, Object> conditionMap, Map<String, Object> setMap);


    /**
     * @return DeleteResult
     * @Author xqx
     * @Description 删除模板信息
     * @Date 15:32 2020/7/20
     * @Param map 删除的模板信息
     **/
    DeleteResult removeTemplateByCondition(Map<String, Object> map);

    /**
     * @return
     * @Author xqx
     * @Description 删除所有的模板数据
     * @Date 17:36 2020/7/20
     * @Param
     **/
    DeleteResult removeAllTemplates();

    /**
     * @return
     * @Author xqx
     * @Description 获取所有模板对象
     * @Date 15:33 2020/7/20
     * @Param t 返回类
     **/
    List<T> findAllTemplateList(Class<T> t);

    /**
     * @return List
     * @Author xqx
     * @Description 根据条件获取模板信息集合
     * @Date 15:38 2020/7/20
     * @Param map 查询条件
     * @Param t 返回类型
     **/
    List<T> findTemplateListByCondition(Map<String, Object> map, Class<T> t);

    /**
     * @return
     * @Author xqx
     * @Description 根据条件获取一条模板信息
     * @Date 15:41 2020/7/20
     * @Param map 查询条件
     * @Param t 返回类型
     **/
    T findTemplateByCondition(Map<String, Object> map, Class<T> t);

    /**
     * @return
     * @Author xqx
     * @Description 获取所有的模板的数量
     * @Date 15:43 2020/7/20
     * @Param
     **/
    Long countAllTemplates();

    /**
     * @return
     * @Author xqx
     * @Description 获取指定条件的模板的数量
     * @Date 15:44 2020/7/20
     * @Param map 查询条件参数
     **/
    Long countTemplateByCondition(Map<String, Object> map);

}
