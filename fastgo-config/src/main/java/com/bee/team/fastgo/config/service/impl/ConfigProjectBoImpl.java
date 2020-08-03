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
import java.util.stream.Collectors;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/7/21
 * @desc 项目配置
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

        Map<String, Object> returnMap = new HashMap<>();

        // 基础配置
        returnMap.put(MongoCommonValue.PROJECT_BASE_KEY, baseConfig((Map<String, Object>) map.get(MongoCommonValue.PROJECT_BASE_KEY)));
        map.remove(MongoCommonValue.PROJECT_BASE_KEY);

        // swagger配置
        returnMap.put("swagger", swaggerConfig());

        // 获取所有的模板集合
        List<Map<String, Object>> list = configTemplateBo.findAllTemplateList(Map.class);

        // 遍历模板，如果新增的配置中包含模板中的软件，就将模板的信息新增到项目中，并且修改用户自定义的参数
        for (Map e : list) {
            // 如果包含模板中的name，就将模板的数据添加到map中
            if (map.containsKey(e.get(MongoCommonValue.TEMPLATE_NAME))) {
                Map<String, Object> map1 = (Map<String, Object>) map.get(e.get(MongoCommonValue.TEMPLATE_NAME));
                // 过滤name、id和code
                e.remove(MongoCommonValue.CONFIG_TEMPLATE_ID);
                e.remove(MongoCommonValue.TEMPLATE_CODE);
                e.remove(MongoCommonValue.TEMPLATE_DESCRIPTION);
                String softName = e.get(MongoCommonValue.TEMPLATE_NAME).toString();
                // 定制mysql配置
                returnMap.putAll(softConfig(softName, map1, e));
                map.remove(softName);
            }
        }

        // 如果包含其他配置信息，直接添加到项目配置中
        if (!map.isEmpty()) {
            returnMap.putAll(map);
        }

        // 添加成功获取返回的基础base配置
        Map<String, Object> returnBaseMap = (Map<String, Object>) template.insert(returnMap, MongoCollectionValue.CONFIG_PROJECT).get(MongoCommonValue.PROJECT_BASE_KEY);
        return returnBaseMap.get(MongoCommonValue.PROJECT_CODE).toString();
    }


    @Override
    public DeleteResult removeOneProject(Map map) {
        if (map.isEmpty()) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数为空");
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
        Update update = new Update().unset(key.replace(".", "-"));
        return template.updateFirst(query, update, MongoCollectionValue.CONFIG_PROJECT);
    }

    @Override
    public String getOneProjectConfigToJSON(Map map) {
        if (map.isEmpty()) {
            return null;
        }

        // 根据条件获取配置信息
        Map<String, Object> configMap = (Map<String, Object>) this.getOneProjectConfigInfo(map, Map.class);
        // 过滤id
        configMap.remove(MongoCommonValue.CONFIG_PROJECT_ID);

        Map<String, Object> baseMap = (Map<String, Object>) configMap.get(MongoCommonValue.PROJECT_BASE_KEY);
        baseMap.remove(MongoCommonValue.PROJECT_NAME);
        baseMap.remove(MongoCommonValue.PROJECT_DESCRIPTION);
        baseMap.remove(MongoCommonValue.PROJECT_CODE);
        baseMap.remove(MongoCommonValue.PROJECT_IP);

        // 提取key
        List<Object> list = Arrays.asList(configMap.keySet().toArray());

        // 将内嵌文档的内容提取出来，并且放到新的map对象中
        Map<String, Object> newMap = new HashMap<>();
        list.stream().forEach(e -> {
            Map<String, Object> inMap = (Map<String, Object>) configMap.get(e.toString());
            inMap.remove(MongoCommonValue.TEMPLATE_DESCRIPTION);
            newMap.putAll(inMap);
        });

        // 返回新map的json格式数据
        return JSONObject.toJSONString(newMap);
    }

    @Override
    public UpdateResult updateOneProject(Map queryMap, Map updateMap) {
        // 修改条件，相当于mysql where子句
        Criteria criteria = new Criteria();
        List<Object> list = Arrays.asList(queryMap.keySet().toArray());
        list.forEach(e -> criteria.and(e.toString()).is(queryMap.get(e)));
        Query query = new Query(criteria);

        // 要修改的键值对,相当于set语句
        Update update = new Update();
        // 获取要修改的所有软件名
        List<Object> listSet = Arrays.asList(updateMap.keySet().toArray());
        List<String> newSofts = listSet.stream().map(e -> e.toString().substring(0, e.toString().indexOf("."))).collect(Collectors.toList());

        // 获取原本项目的所有软件名
        List<Object> oldSofts = Arrays.asList(template.findOne(query, Map.class, MongoCollectionValue.CONFIG_PROJECT).keySet().toArray());

        // 如果有新增的软件，就将模板中该软件的配置信息取出来作为该软件的配置信息
        for (String e : newSofts) {
            // 如果不包含就增加基础配置
            if (!oldSofts.contains(e)) {
                // 根据软件名查询模板信息
                Map<String, Object> map = new HashMap<>();
                map.put(MongoCommonValue.TEMPLATE_NAME, e);
                Map<String, Object> tempMap = (Map<String, Object>) configTemplateBo.findTemplateByCondition(map, Map.class);

                // 过滤name、id和code
                tempMap.remove(MongoCommonValue.CONFIG_TEMPLATE_ID);
                tempMap.remove(MongoCommonValue.TEMPLATE_CODE);
                tempMap.remove(MongoCommonValue.TEMPLATE_DESCRIPTION);

                // 转移到修改的map中
                update.set(e, softConfig(e, (Map<String, Object>) updateMap.get(e), tempMap).get(e));

                // 清除已经修改的软件数据软件数据
                updateMap.remove(e);
                listSet.remove(e);
            }
        }

        // 修改已存在的软件配置
        listSet.forEach(e -> update.set(e.toString(), updateMap.get(e)));

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
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
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

    private Map<String, Object> softConfig(String templateName, Map<String, Object> project, Map<String, Object> template) {
        // 软件定制，根据不同的软件进行定制
        Map<String, Object> map = new HashMap<>();
        switch (templateName) {
            case "mysql":
                map.put("mysql", mysqlConfig(project, template));
                break;
            case "redis":
                map.put("redis", redisConfig(project, template));
                break;
            case "kafka":
                map.put("kafka", kafkaConfig(project, template));
                break;
            default:
                map.put(templateName,project);
        }
        return map;
    }

    private Map<String, Object> baseConfig(Map<String, Object> project) {
        // 定制基础的项目配置
        Map<String, Object> map = new HashMap<>();
        project.put("server.port", project.get("port"));
        project.remove("port");
        project.put("configCode", RandomUtils.getRandomStr(16));
        return project;
    }

    private Map<String, Object> mysqlConfig(Map<String, Object> project, Map<String, Object> template) {
        // project 项目里面的软件配置，template 软件的模板
        // 定制mysql的项目配置
        template.remove(MongoCommonValue.TEMPLATE_NAME);
        template.replace("spring.simple.datasource.url", ("jdbc:mysql://" + project.get("ip") + ":" + project.get("port") + "/" + project.get("dataSourceName") + "?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&autoReconnect=true&serverTimezone=CTT"));
        template.replace("spring.simple.datasource.username", project.get("userName"));
        template.replace("spring.simple.datasource.password", project.get("password"));
        return template;
    }

    private Map<String, Object> redisConfig(Map<String, Object> project, Map<String, Object> template) {
        // project 项目里软件的基础配置
        // 定制redis配置
        template.remove(MongoCommonValue.TEMPLATE_NAME);
        template.replace("spring.simple.redisHost", project.get("ip"));
        template.replace("spring.simple.redisPort", project.get("port"));
        template.replace("spring.simple.redisPwd", project.get("password"));
        return template;
    }

    private Map<String, Object> swaggerConfig() {
        Map<String, Object> map = new HashMap<>();
        map.put("spring.simple.swagger.is_enable", "true");
        return map;
    }

    private Map<String, Object> kafkaConfig(Map<String, Object> project, Map<String, Object> template) {
        template.remove(MongoCommonValue.TEMPLATE_NAME);
        template.replace("spring.simple.kafka.bootstrap.servers", project.get("ip") + ":" + project.get("port"));
        return template;
    }
}
