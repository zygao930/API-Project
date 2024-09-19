package com.project.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public ResponseEntity<?> logout(String token) {
        // Retrieve the username associated with the token from Redis
        String username = redisTemplate.opsForValue().get("auth:user:" + token);

        if (username != null) {
            // Remove the token and user session from Redis
            redisTemplate.delete("auth:token:" + username);
            redisTemplate.delete("auth:user:" + token);
            return ResponseEntity.ok("Logout successful.");
        }

        return ResponseEntity.status(401).body("Invalid token or session expired.");
    }
}
