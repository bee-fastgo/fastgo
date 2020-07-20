package com.bee.team.fastgo.config.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @ClassName MongoConfig
 * @Description mongo自定义配置修改
 * @Author xqx
 * @Date 2020/7/20 13:57
 * @Version 1.0
 **/
@Configuration
public class MongoConfig {
    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;
    @Autowired
    private MongoMappingContext mongoMappingContext;

    @Bean
    public MappingMongoConverter mongoConverter() throws Exception {
        // 解决mongodb中的key不可以带"."的问题，在mongodb中以"-"分割存储key，但是返回给java的时候以"."分割
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        //this is my customization
        mongoConverter.setMapKeyDotReplacement("-");
        mongoConverter.afterPropertiesSet();
        return mongoConverter;
    }
}
