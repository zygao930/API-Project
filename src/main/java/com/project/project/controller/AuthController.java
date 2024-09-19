package com.project.project.controller;

import com.project.project.security.AuthService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private AuthService authService;

    @PostMapping("/generateCaptcha")
    public ResponseEntity<String> generateCaptcha(){
        String captcha = RandomStringUtils.randomAlphanumeric(6);
        String captchaKey = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(captchaKey, captcha, 30, TimeUnit.MINUTES);
        Map<String, String> response = new HashMap<>();
        response.put("captcha", captcha);
        response.put("captchaKey", captchaKey);
        System.out.println("captcha: "+ captcha);
        System.out.println("captchaKey: "+ captchaKey);

        // Return the response with status 200 OK
        return ResponseEntity.ok(response.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> loginData) throws Exception {
        String username = loginData.get("username");
        String encryptedPassword = loginData.get("encryptedPassword");
        String captcha = loginData.get("captcha");
        String captchaKey = loginData.get("captchaKey");

        // Use the AuthService to handle the login logic
        return authService.login(username, encryptedPassword, captcha, captchaKey);
    }
}
