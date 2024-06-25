package com.spbt.secproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${fileUploadPath}")
    String fileUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
         * 파일 업로드 워게임 업로드 파일 매핑
         * */
        registry.addResourceHandler("/rsc/img/user/**")
                .addResourceLocations(fileUploadPath);
    }

}
