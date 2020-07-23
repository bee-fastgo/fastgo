package com.bee.team.fastgo.config;

import com.bee.team.fastgo.config.service.ConfigTemplateBo;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestTemplate
 * @Description 模板测试类
 * @Author xqx
 * @Date 2020/7/20 16:57
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTemplate {
    @Autowired
    private ConfigTemplateBo configTemplateBo;

    // 1.添加模板
    @Test
    public void testInsertOneTemplate() {
        Map<String, Object> map = new HashMap<>();
        map.put("com.name", "张456");
        map.put("com.age", 20);
        map.put("com.sex", "男");
        map.put("com.country", "中国");
        map.put("com.phone", "123456");
        System.out.println(configTemplateBo.insertTemplate(map));

    }

    // 2.批量添加模板
    @Test
    public void testInsertTemplateList() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "张三");
        map1.put("age", 21);
        map1.put("sex", "女");
        map1.put("country", "中国");
        map1.put("phone", "12454456");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "李四");
        map2.put("age", 30);
        map2.put("sex", "男");
        map2.put("country", "美国");
        map2.put("phone", "12000056");
        list.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "王五");
        map3.put("age", 10);
        map3.put("sex", "女");
        map3.put("country", "缅甸");
        map3.put("phone", "1266660056");
        list.add(map3);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("name", "刘三");
        map4.put("age", 80);
        map4.put("sex", "男");
        map4.put("country", "中国");
        map4.put("phone", "1233336");
        list.add(map4);
        Map<String, Object> map5 = new HashMap<>();
        map5.put("name", "田七");
        map5.put("age", 10);
        map5.put("sex", "女");
        map5.put("country", "俄罗斯");
        map5.put("phone", "120456456");
        list.add(map5);
        List<Map<String, Object>> list1 = (List<Map<String, Object>>) configTemplateBo.insertManyTemplateList(list);
        System.out.println("批量添加模板成功，添加的模板为：" + list1);
    }

    // 3.修改模板
    @Test
    public void testUpdateTemplate() {
        Map<String, Object> query = new HashMap<>();
        query.put("name", "张三");
        query.put("age", 20);
        Map<String, Object> update = new HashMap<>();
        update.put("name", "涨价的啥子");
        update.put("s.country", "沙漠地图");
        update.put("county", "米拉码");
        UpdateResult result = configTemplateBo.updateTemplate(query, update);
        System.out.println(result);
    }

    // 4.删除模板
    @Test
    public void testRemoveOneTemplate() {
        Map<String, Object> query = new HashMap<>();
        query.put("name", "田七");
        query.put("age", 10);
        DeleteResult result = configTemplateBo.removeTemplateByCondition(query);
        System.out.println(result);
    }

    // 删除所有的模板
    @Test
    public void testRemoveAllTemplates() {
        System.out.println(configTemplateBo.removeAllTemplates());
    }

    // 5.获取所有的模板信息
    @Test
    public void testGetAllTemplateList() {
        List<Map<String, Object>> map = configTemplateBo.findAllTemplateList(Map.class);
        System.out.println(map);

    }

    // 6.根据条件获取模板信息集合
    @Test
    public void testGetTemplateListByCondition() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 21);
        List<Map<String, Object>> map1 = configTemplateBo.findTemplateListByCondition(map, Map.class);
        System.out.println(map1);
    }

    // 7.获取一条模板信息
    @Test
    public void testGetOnrTemplateByCondition() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 20);
        Map<String, Object> map1 = (Map<String, Object>) configTemplateBo.findTemplateByCondition(map, Map.class);
        System.out.println(map1);
    }

    // 8.获取所有的模板数量
    @Test
    public void testCountAllTemplate() {
        System.out.println(configTemplateBo.countAllTemplates());
    }

    // 9.获取指定条件的模板数量
    @Test
    public void testCountTemplatesByCondition() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
//        map.put("age", 20);
        Long count = configTemplateBo.countTemplateByCondition(map);
        System.out.println(count);
    }

    @Test
    public void testStr() {
        String str = "com.jjj.ggg.hhh";
        System.out.println(str.replace(".", "-"));
    }

    @Test
    public void test7() {
        System.out.println(configTemplateBo.removeOneDataByCondition("d9vwPOoNvk4iUWrs", "spring-simple-datasource-initialSize"));
    }
}
