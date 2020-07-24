package com.bee.team.fastgo.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.config.common.MongoCollectionValue;
import com.bee.team.fastgo.config.common.MongoCommonValue;
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
            if (map.containsKey(e.get(MongoCommonValue.TEMPLATE_NAME))) {
                Map<String, Object> map1 = (Map<String, Object>) map.get(e.get("name"));
                // 过滤name、id和code
                e.remove(MongoCommonValue.TEMPLATE_NAME);
                e.remove(MongoCommonValue.CONFIG_TEMPLATE_ID);
                e.remove(MongoCommonValue.TEMPLATE_CODE);
                map1.putAll(e);
            }
        });
        // 给项目设置唯一标识
        Map<String, Object> newInsertBaseMap = (Map<String, Object>) map.get(MongoCommonValue.PROJECT_BASE_KEY);
        newInsertBaseMap.put(MongoCommonValue.PROJECT_CODE, RandomUtils.getRandomStr(16));
        map.remove(MongoCommonValue.PROJECT_BASE_KEY);
        map.put(MongoCommonValue.PROJECT_BASE_KEY, newInsertBaseMap);
        // 添加成功获取返回的基础base配置
        Map<String, Object> returnBaseMap = (Map<String, Object>) template.insert(map, MongoCollectionValue.CONFIG_PROJECT).get(MongoCommonValue.PROJECT_BASE_KEY);
        return returnBaseMap.get(MongoCommonValue.PROJECT_CODE).toString();
    }

    @Override
    public DeleteResult removeOneProject(Map map) {
        if (map.isEmpty()) {
            // 抛出异常
        }
        List<Object> list = Arrays.asList(map.keySet().toArray());
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
    public UpdateResult removeOneDataByCondition(String code, String key) {
        // MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE = base.configCode
        Query query = new Query(Criteria.where(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE).is(code));
        Update update = new Update().unset(key);
        return template.updateFirst(query, update, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public String getOneProjectConfigToJSON(Map map) {
        // 根据条件获取配置信息
        Map<String, Object> configMap = (Map<String, Object>) this.getOneProjectConfigInfo(map, Map.class);

        // 过滤id
        configMap.remove(MongoCommonValue.CONFIG_PROJECT_ID);

        Map<String, Object> baseMap = (Map<String, Object>) configMap.get("base");
        baseMap.remove(MongoCommonValue.PROJECT_NAME);
        baseMap.remove(MongoCommonValue.PROJECT_DESCRIPTION);
        baseMap.remove(MongoCommonValue.PROJECT_CODE);

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
        List<Object> list = Arrays.asList(queryMap.keySet().toArray());
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
            List<Object> list = Arrays.asList(map.keySet().toArray());
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
        List<Object> list = Arrays.asList(map.keySet().toArray());
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
            List<Object> list = Arrays.asList(map.keySet().toArray());
            list.stream().forEach(key -> criteria.and(key.toString()).regex(".*" + map.get(key) + ".*"));
            query.addCriteria(criteria);
        }
        return template.count(query, MongoCollectionValue.CONFIG_PROJECT);
    }

}
