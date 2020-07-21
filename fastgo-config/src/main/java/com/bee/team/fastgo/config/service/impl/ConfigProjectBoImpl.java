package com.bee.team.fastgo.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.config.common.MongoCollectionValue;
import com.bee.team.fastgo.config.service.ConfigProjectBo;
import com.bee.team.fastgo.config.service.ConfigTemplateBo;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
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
    public Map<String, Object> insertProject(Map map) {
        // map不能为空
        if (map.isEmpty()) {
            // 抛出异常
        }
        if (map.entrySet().isEmpty()) {
            // 抛出异常
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
                map1.putAll(e);
            }
        });
        return template.insert(map, MongoCollectionValue.CONFIG_PROJECT);
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
        Map<String, Object> map1 = (Map<String, Object>) this.getOneProjectConfigInfo(map, Map.class);

        // 过滤id
        map1.remove("_id");

        // 提取key
        List<Object> list = Arrays.asList(map1.keySet().toArray());

        // 将内嵌文档的内容提取出来，并且放到新的map对象中
        Map<String, Object> map2 = new HashMap<>();
        list.stream().forEach(e -> map2.putAll((Map<String, Object>) map1.get(e.toString())));

        // 返回新map的json格式数据
        return JSONObject.toJSONString(map2);
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
            list.stream().forEach(key -> criteria.and(key.toString()).is(map.get(key)));
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
            list.stream().forEach(key -> criteria.and(key.toString()).is(map.get(key)));
            query.addCriteria(criteria);
        }
        return template.count(query, MongoCollectionValue.CONFIG_PROJECT);
    }

}
