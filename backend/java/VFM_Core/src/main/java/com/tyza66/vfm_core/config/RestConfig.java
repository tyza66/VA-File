package com.tyza66.vfm_core.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Author: tyza66
 * Date: 2023/7/23 9:24
 * Github: https://github.com/tyza66
 **/

@Configuration
public class RestConfig {
    //定义RestTemplateBean 用于将来向其他MIND端发送请求
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate build = builder.build();
        return build;
    }
}
