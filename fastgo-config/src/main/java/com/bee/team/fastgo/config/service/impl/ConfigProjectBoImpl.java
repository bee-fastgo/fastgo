package com.bee.team.fastgo.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.config.common.MongoCollectionValue;
import com.bee.team.fastgo.config.service.ConfigProjectBo;
import com.bee.team.fastgo.config.service.ConfigTemplateBo;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @ClassName ConfigProjectBoImpl
 * @Description 项目配置
 * @Author xqx
 * @Date 2020/7/21 9:19
 * @Version 1.0
 **/
@Service
public class ConfigProjectBoImpl implements ConfigProjectBo {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private ConfigTemplateBo configTemplateBo;

    @Override
    public String insertProject(Map map) {
        // map不能为空
        if (map.isEmpty()) {
            // 抛出异常
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }
        if (map.entrySet().isEmpty()) {
            // 抛出异常
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }
        // 获取所有的模板集合
        List<Map<String, Object>> list = configTemplateBo.findAllTemplateList(Map.class);
        list.stream().forEach(e -> {
            // 如果包含模板中的name，就将模板的数据添加到map中
            if (map.containsKey(e.get("name"))) {
                Map<String, Object> map1 = (Map<String, Object>) map.get(e.get("name"));
                // 过滤name和id
                e.remove("name");
                e.remove("_id");
                e.remove("code");
                map1.putAll(e);
            }
        });
        // 给项目设置唯一标识
        Map<String, Object> newInsertBaseMap = (Map<String, Object>) map.get("base");
        newInsertBaseMap.put("configCode", RandomUtils.getRandomStr(16));
        map.remove("base");
        map.put("base", newInsertBaseMap);
        // 添加成功获取返回的基础base配置
        Map<String, Object> returnBaseMap = (Map<String, Object>) template.insert(map, MongoCollectionValue.CONFIG_PROJECT).get("base");
        return returnBaseMap.get("configCode").toString();
    }

    @Override
    public DeleteResult removeOneProject(Map map) {
        if (map.isEmpty()) {
            // 抛出异常
        }
        List<Object> list = Arrays.asList(map.keySet());
        Criteria criteria = new Criteria();
        list.stream().forEach(key -> criteria.and(key.toString()).is(map.get(key)));
        Query query = new Query(criteria);
        return template.remove(query, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public DeleteResult removeAllProjects() {
        Query query = new Query();
        return template.remove(query, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public String getOneProjectConfigToJSON(Map map) {
        // 根据条件获取配置信息
        Map<String, Object> configMap = (Map<String, Object>) this.getOneProjectConfigInfo(map, Map.class);

        // 过滤id
        configMap.remove("_id");

        Map<String, Object> baseMap = (Map<String, Object>) configMap.get("base");
        baseMap.remove("name");
        baseMap.remove("description");
        baseMap.remove("configCode");

        // 提取key
        List<Object> list = Arrays.asList(configMap.keySet().toArray());

        // 将内嵌文档的内容提取出来，并且放到新的map对象中
        Map<String, Object> newMap = new HashMap<>();
        list.stream().forEach(e -> newMap.putAll((Map<String, Object>) configMap.get(e.toString())));

        // 返回新map的json格式数据
        return JSONObject.toJSONString(newMap);
    }

    @Override
    public UpdateResult updateOneProject(Map queryMap, Map updateMap) {
        // 修改条件，相当于mysql where子句
        Criteria criteria = new Criteria();
        List<Object> list = Arrays.asList(queryMap.keySet());
        list.stream().forEach(e -> criteria.and(e.toString()).is(queryMap.get(e)));
        Query query = new Query(criteria);

        // 要修改的键值对,相当于set语句
        Update update = new Update();
        List<Object> listSet = Arrays.asList(updateMap.keySet().toArray());
        listSet.stream().forEach(e -> update.set(e.toString(), updateMap.get(e)));
        // 修改指定的值，如果数据不存在键就添加该键值对
        return template.updateFirst(query, update, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public List getAllProjectConfigList(Class t) {
        return template.findAll(t, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public List getProjectConfigList(Map map, Class t) {
        Query query = new Query();
        // 获取所有的key,如果map是空，就默认查询所有的信息
        if (!map.isEmpty()) {
            Criteria criteria = new Criteria();
            List<Object> list = Arrays.asList(map.keySet());
            list.stream().forEach(key -> criteria.and(key.toString()).regex(".*" + map.get(key) + ".*"));
            query.addCriteria(criteria);
        }
        return template.find(query, t, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public Object getOneProjectConfigInfo(Map map, Class t) {
        if (map.isEmpty()) {
            // 抛出异常
        }
        List<Object> list = Arrays.asList(map.keySet());
        Criteria criteria = new Criteria();
        list.stream().forEach(key -> criteria.and(key.toString()).is(map.get(key)));
        Query query = new Query(criteria);
        return template.findOne(query, t, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public Long countAllProjectConfig() {
        Query query = new Query();
        return template.count(query, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public Long countProjectByCondition(Map map) {
        Query query = new Query();
        // 获取所有的key,如果map是空，就默认查询所有的信息
        if (!map.isEmpty()) {
            Criteria criteria = new Criteria();
            List<Object> list = Arrays.asList(map.keySet());
            list.stream().forEach(key -> criteria.and(key.toString()).regex(".*" + map.get(key) + ".*"));
            query.addCriteria(criteria);
        }
        return template.count(query, MongoCollectionValue.CONFIG_PROJECT);
    }

}
