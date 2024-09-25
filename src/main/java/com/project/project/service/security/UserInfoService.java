package com.project.project.service.security;

import com.project.project.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * This service provides user information retrieval from Redis.
 * It implements the logic to load user details by username,
 * handling user existence checks and exception throwing.
 */
@Service
public class UserInfoService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate; // Redis template for user data retrieval

    /**
     * Loads user details by username from Redis.
     *
     * @param username The username of the user to be loaded.
     * @return UserDetails containing the user's information.
     * @throws CommonException If the user does not exist in Redis.
     */
    public UserDetails loadUserByUsername(String username) throws CommonException {
        // Retrieve user information from Redis
        String user = redisTemplate.opsForValue().get("user:" + username);

        // Check if the user exists; if not, throw an exception
        if (user == null) {
            throw new CommonException(404, "用户不存在"); // User does not exist
        }

        // Build and return UserDetails object with dummy password and authority
        return User.withUsername(username)
                .authorities("TEST") // Example authority
                .password("{noop}123456") // No password encoding for example purposes
                .build();
    }
}
