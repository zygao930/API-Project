package com.project.project.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service to handle Redis operations for storing and retrieving verification codes.
 */
@Service("emailRedisService")
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * Stores the verification code in Redis with a 20-minute expiry.
     *
     * @param email Email address as the key.
     * @param code  Verification code to be stored.
     */
    public void storeCode(String email, String code) {
        String key = "email:code:" + email.trim();
        redisTemplate.opsForValue().set(key, code, 20, TimeUnit.MINUTES);
    }

    /**
     * Retrieves the verification code from Redis for the given email.
     *
     * @param email Email address used to fetch the code.
     * @return The stored verification code.
     */
    public String getCode(String email) {
        String key = "email:code:" + email.trim();
        return redisTemplate.opsForValue().get(key);
    }
}
