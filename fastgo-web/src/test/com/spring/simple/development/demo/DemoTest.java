package com.spring.simple.development.demo;

import com.bee.team.fastgo.service.TestDemoBo;
import com.spring.simple.development.core.annotation.config.EnableMybatis;
import com.spring.simple.development.core.annotation.config.EnableSwagger;
import com.spring.simple.development.core.annotation.config.EnableWebMvc;
import com.spring.simple.development.core.annotation.config.SpringSimpleApplication;
import com.spring.simple.development.core.baseconfig.context.SimpleContentApplication;
import com.spring.simple.development.core.baseconfig.tomcat.SimpleApplication;
import org.junit.Before;
import org.junit.Test;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:20
 * @Description 程序启动
 **/
@EnableWebMvc
@EnableSwagger
@EnableMybatis
@SpringSimpleApplication
public class DemoTest {

    @Before
    public void simpleTestBefore() {
        SimpleApplication.runTest(DemoTest.class);
    }
    @Test
    public void test() {
        TestDemoBo testDemoBo = SimpleContentApplication.getBeanByType(TestDemoBo.class);
        testDemoBo.getData("1");
    }
}