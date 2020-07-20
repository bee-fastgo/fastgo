package com.bee.team.fastgo.config;

import com.bee.team.fastgo.common.SoftwareEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestApplication {
    @Autowired
    private MongoDbBo mongoDbBo;

    @Test
    public void testFindOne() {
        Map<String, Object> map = mongoDbBo.getUser("李四");
        System.out.println(map);
    }

    @Test
    public void testInsertOne() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("server.port", "8080");
        map1.put("projectName", "fastgo-config");
        map1.put("projectCode", "4564546646464");
        Map<String, Object> map2 = new HashMap<>();
        map1.put("mysql", map2);
        map2.put("spring.simple.datasource.driverClassName", "com.mysql.jdbc.Driver");
        map2.put("spring.simple.datasource.url", "jdbc:mysql://172.22.5.248:3306/funder?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&autoReconnect=true&serverTimezone=CTT");
        map2.put("spring.simple.datasource.username", "root");
        map2.put("spring.simple.datasource.password", "123456");
        map2.put("spring.simple.datasource.initialSize", "20");
        map2.put("spring.simple.datasource.minIdle", "20");
        mongoDbBo.insertUser(map1);
    }

    @Test
    public void testEnum() {
        List<String> list = Arrays.stream(SoftwareEnum.values())
                .map(SoftwareEnum::name)
                .collect(Collectors.toList());
//        List<String> list1=list.stream()
//                .map(e->SoftwareEnum.valueOf(e)).collect(Collectors.toList());
    }
}
