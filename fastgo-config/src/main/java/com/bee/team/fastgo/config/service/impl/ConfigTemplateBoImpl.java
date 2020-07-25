package com.bee.team.fastgo.config.service.impl;

import com.bee.team.fastgo.config.common.MongoCollectionValue;
import com.bee.team.fastgo.config.common.MongoCommonValue;
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
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/7/20
 * @desc 模板操作
 **/
@Service
public class ConfigTemplateBoImpl<T> implements ConfigTemplateBo {
    @Autowired
    private MongoTemplate template;

    @Override
    public Map<String, Object> insertTemplate(Map map) {
        if (map.isEmpty()) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "参数不能为空");
            // 抛出异常
        }
        map.put(MongoCommonValue.TEMPLATE_CODE, RandomUtils.getRandomStr(16));
        return template.insert(map, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public Collection<Map<String, Object>> insertManyTemplateList(List list) {
        if (CollectionUtils.isEmpty(list)) {
            // 抛出异常
        }
        return template.insert(list, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public DeleteResult removeTemplateByCondition(Map map) {
        if (map.isEmpty()) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }
        // 获取所有的条件key
        List<Object> keys = Arrays.asList(map.keySet().toArray());
        Criteria criteria = new Criteria();
        keys.stream().forEach(e -> criteria.and(e.toString()).is(map.get(e)));
        Query query = new Query(criteria);
        // 根据条件删除指定的模板信息
        return template.remove(query, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public UpdateResult removeOneDataByCondition(String code, String key) {
        Query query = new Query(Criteria.where(MongoCommonValue.TEMPLATE_CODE.replace(".", "-")).is(code));
        Update update = new Update().unset(key);
        return template.updateFirst(query, update, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public DeleteResult removeAllTemplates() {
        Query query = new Query();
        return template.remove(query, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public UpdateResult updateTemplate(Map conditionMap, Map setMap) {
        if (conditionMap.isEmpty()) {
            // 抛出异常
        }
        if (setMap.isEmpty()) {
            // 抛出异常
        }
        // 获取所有的条件key
        List<Object> conditionKeys = Arrays.asList(conditionMap.keySet().toArray());

        // 获取所有要修改的key
        List<Object> updateKeys = Arrays.asList(setMap.keySet().toArray());

        // 自定义查询条件,修改的条件
        Criteria criteria = new Criteria();
        conditionKeys.stream().forEach(e -> criteria.and(e.toString()).is(conditionMap.get(e)));
        Query query = new Query(criteria);

        // 修改的键值
        Update update = new Update();
        updateKeys.stream().forEach(e -> update.set(e.toString(), setMap.get(e)));

        // 根据指定条件修改模板信息，并且返回修改结果
        return template.updateFirst(query, update, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public List findAllTemplateList(Class t) {
        return template.findAll(t, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public Object findTemplateByCondition(Map map, Class t) {
        if (map.isEmpty()) {
            // 抛出异常
        }
        List<Object> list = Arrays.asList(map.keySet().toArray());
        // 定义查询条件
        Criteria criteria = new Criteria();
        list.stream().forEach(key -> criteria.and(key.toString()).is(map.get(key)));
        Query query = new Query(criteria);
        return template.findOne(query, t, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public List findTemplateListByCondition(Map map, Class t) {
        Query query = new Query();
        // 如果map为空，就查询所有的模板信息
        if (!map.isEmpty()) {
            List<Object> list = Arrays.asList(map.keySet().toArray());
            // 定义查询条件
            Criteria criteria = new Criteria();
            list.stream().forEach(key -> criteria.and(key.toString()).regex(".*" + map.get(key) + ".*"));
            query.addCriteria(criteria);
        }
        return template.find(query, t, MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public Long countAllTemplates() {
        return template.count(new Query(), MongoCollectionValue.CONFIG_TEMPLATE);
    }

    @Override
    public Long countTemplateByCondition(Map map) {
        Query query = new Query();
        // 如果map为空，就查询所有的模板信息的数量
        if (!map.isEmpty()) {
            List<Object> list = Arrays.asList(map.keySet().toArray());
            // 定义查询条件
            Criteria criteria = new Criteria();
            list.stream().forEach(key -> criteria.and(key.toString()).regex(".*" + map.get(key) + ".*"));
            query.addCriteria(criteria);
        }
        return template.count(query, MongoCollectionValue.CONFIG_TEMPLATE);
    }
}
