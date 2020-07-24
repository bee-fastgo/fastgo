package com.bee.team.fastgo.config;

import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.config.service.ConfigProjectBo;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestProject
 * @Description 项目测试
 * @Author xqx
 * @Date 2020/7/21 11:45
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestProject {
    @Autowired
    private ConfigProjectBo configProjectBo;

    // 1、增加项目配置
    @Test
    public void testInsertProject() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "fastGo-config");
        map1.put("ip", "172.22.5.248");
        map1.put("port", "8080");
        map1.put("description", "极go");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("spring.simple.datasource.driverClassName", "com.mysql.jdbc.Driver");
        map2.put("spring.simple.datasource.url", "jdbc:mysql://172.22.5.248:3306/funder?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&autoReconnect=true&serverTimezone=CTT");
        map2.put("spring.simple.datasource.username", "root");
        map2.put("spring.simple.datasource.password", "123456");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("spring.simple.redisHost", "172.22.5.247");
        map3.put("spring.simple.redisPort", "6379");
        map3.put("spring.simple.redisPwd", "123456");
        Map<String, Object> map = new HashMap<>();
        map.put("base", map1);
        map.put("mysql", map2);
        map.put("redis", map3);
        String code = configProjectBo.insertProject(map);
        System.out.println(code);
    }

    // 2、删除一个项目
    @Test
    public void testRemoveOneProject() {
        Map<String, Object> map = new HashMap<>();
        map.put("mysql.name", "mysql5.7");
        System.out.println(configProjectBo.removeOneProject(map));
    }

    // 3、删除所有项目
    @Test
    public void testRemoveAllProject() {
        System.out.println(configProjectBo.removeAllProjects());
    }

    // 4、修改项目
    @Test
    public void testUpdateProject() {
        Map<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("code", "123456789");
        Map<String, Object> mapSet = new HashMap<>();
        mapSet.put("李四.mysql-name", "不是mysql");
        mapSet.put("李四.mysql-add", "127.0.0.1");
        System.out.println(configProjectBo.updateOneProject(mapQuery, mapSet));
    }

    // 6、获取项目的JSON配置
    @Test
    public void testGetJSONProjectInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("configCode", "UyRbVcaPnho33N85");
        System.out.println(configProjectBo.getOneProjectConfigToJSON(map));
    }

    // 5、获取一条配置
    @Test
    public void testGetOneProject() {
        Map<String, Object> map = new HashMap<>();
        map.put("configCode", "UyRbVcaPnho33N85");
        Map<String, Object> map1 = (Map<String, Object>) configProjectBo.getOneProjectConfigInfo(map, Map.class);
        System.out.println(JSONObject.toJSONString(map1));
    }

    // 6、根据条件获取配置列表信息
    @Test
    public void testGetProjectList() {
        Map<String, Object> map = new HashMap<>();
//        map.put("李四.mysql.name", "mysql5.7");
        List<Map<String, Object>> list = configProjectBo.getProjectConfigList(map, Document.class);
        System.out.println(list);
    }

    // 7、获取所有的配置信息
    @Test
    public void testGetAllProject() {
        System.out.println(configProjectBo.getAllProjectConfigList(Map.class));
    }

    // 8、获取所有的项目的数量
    @Test
    public void testCountAllProject() {
        System.out.println(configProjectBo.countAllProjectConfig());
    }

    // 9、根据条件获取项目的数量
    @Test
    public void testCountProjectByCondition() {
        Map<String, Object> map = new HashMap<>();
        map.put("mysql.name", "mysql5.7");
        System.out.println(configProjectBo.countProjectByCondition(map));
    }

    // 10、移除文档里面的某一条数据
    @Test
    public void removeOneData() {
        System.out.println(configProjectBo.removeOneDataByCondition("UyRbVcaPnho33N85", "spring.simple.datasource.minIdle"));
    }
}
