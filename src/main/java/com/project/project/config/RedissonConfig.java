//package com.project.project.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ResourceLoader;
//
//@Configuration
//public class RedissonConfig {
//
//    private final ResourceLoader resourceLoader;
//
//    public RedissonConfig(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }
//
//    @Bean
//    public RedissonClient redissonClient() {
//        try {
//            // Load the configuration from redisson.yaml
//            Config config = Config.fromYAML(resourceLoader.getResource("classpath:redisson.yaml").getInputStream());
//            return Redisson.create(config);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create RedissonClient", e);
//        }
//    }
//}
