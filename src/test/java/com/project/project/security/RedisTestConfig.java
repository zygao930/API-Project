package com.project.project.security;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisTestConfig {

    @MockBean
    private RedisTemplate<String, String> redisTemplate;

    @Bean
    public RedisTemplate<String, String> redisTemplateBean() {
        return redisTemplate;
    }
}
