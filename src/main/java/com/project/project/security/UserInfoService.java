package com.project.project.security;

import com.project.project.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static io.netty.handler.ssl.SslContextOption.exists;

@Service
public class UserInfoService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public UserDetails loadUserByUsername(String username) throws CommonException {
        String user = redisTemplate.opsForValue().get("user:" + username);
        if (user == null) {
            throw new CommonException(404, "用户不存在");
        }
        return User.withUsername(username).authorities("TEST").password("{noop}123456").build();
    }


}
