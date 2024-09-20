package com.project.project.controller;
import lombok.Data;
import lombok.Getter;
import com.project.project.exception.CommonException;
import com.project.project.security.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/generateCaptcha")
    public ResponseEntity<String> generateCaptcha() {
        String captcha = RandomStringUtils.randomAlphanumeric(6);
        String captchaKey = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(captchaKey, captcha, 30, TimeUnit.MINUTES);
        Map<String, String> response = new HashMap<>();
        response.put("captcha", captcha);
        response.put("captchaKey", captchaKey);
        System.out.println("captcha: " + captcha);
        System.out.println("captchaKey: " + captchaKey);

        // Return the response with status 200 OK
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Validate input
        String nickName = loginRequest.getNickName();
        String captcha = loginRequest.getCaptcha();
        String captchaKey = loginRequest.getCaptchaKey();

        if (nickName == null || captcha == null || captchaKey == null) {
            return ResponseEntity.badRequest().body("Invalid input: All fields are required.");
        }

        try {
            // Use the AuthService to handle the login logic
            return authService.login(nickName, captcha, captchaKey);
            // If you plan to include the password in the future, add that logic here.
        } catch (Exception e) {
            // Log the error instead of printing stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<Map<String, String>> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Invalid Authorization header"));
        }

        String token = authorizationHeader.substring(7);
        System.out.println("Received token: " + token);

        String username = tokenService.extractUsername(token);
        System.out.println("Extracted username: " + username);

        UserDetails userDetails;
        try {
            userDetails = userInfoService.loadUserByUsername(username);
        } catch (CommonException e) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "User not found"));
        }

        if (!tokenService.validateToken(token, userDetails)) {
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid token"));
        }

        // Create a response object with user info and token
        Map<String, String> response = new HashMap<>();
        response.put("username", userDetails.getUsername());
        response.put("token", token); // Include the token in the response

        return ResponseEntity.ok(response);
    }


    @DeleteMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
        String nickName = logoutRequest.getNickName();

        // 检查用户是否在线
        String token = redisTemplate.opsForValue().get(nickName);
        System.out.println("token: " + token);
        if (token == null) {
            return ResponseEntity.status(400).body("User is not logged in.");
        }

        // 删除用户数据
        boolean deleted = redisTemplate.delete(nickName);
        if (deleted) {
            return ResponseEntity.ok("User logged out successfully.");
        } else {
            return ResponseEntity.status(400).body("Failed to log out user.");
        }
    }


}

