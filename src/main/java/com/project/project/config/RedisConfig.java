package com.project.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for setting up RedisTemplate.
 * This class provides the necessary configuration for Redis
 * operations in the application.
 */
@Configuration
public class RedisConfig {
    /**
     * Creates a RedisTemplate bean for interacting with Redis.
     *
     * @param connectionFactory the RedisConnectionFactory used to create connections to Redis.
     * @return a configured RedisTemplate for Redis operations.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Set up serializers for keys and values
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setDefaultSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        return template;
    }
}
