package com.bee.team.fastgo.hander;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xqx
 * @date 2020/7/28 15:16
 * @desc 静态资源映射
 **/

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${server.file.root.parh}")
    public String fileRootPath;

    @Value("${server.file.pattern.path}")
    public String pathPatterns;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(pathPatterns).addResourceLocations("file:" + fileRootPath);
    }
    @Bean(name="multipartResolver")
    public MultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }

}
