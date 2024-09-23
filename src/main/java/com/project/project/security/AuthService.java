package com.project.project.security;

import com.project.project.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CustomizeUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    /**
     * Handles the login process: Captcha validation, user lookup, and token generation.
     * @param nickName The user's nickname.
     * @param captcha The captcha provided by the user.
     * @param captchaKey The key to retrieve the captcha from Redis.
     * @return ResponseEntity with either success or error message.
     */
    public ResponseEntity<String> login(String nickName, String captcha, String captchaKey) {
        try {
            // Validate the captcha from Redis
            String storedCaptcha = redisTemplate.opsForValue().get(captchaKey);
            if (storedCaptcha == null) {
                logger.warn("Captcha for key {} not found in Redis", captchaKey);
                throw new CommonException(300, "Captcha error or expired");
            }
            if (!storedCaptcha.equals(captcha)) {
                logger.warn("Captcha mismatch for key {}: expected {}, got {}", captchaKey, storedCaptcha, captcha);
                throw new CommonException(300, "Captcha error or expired");
            }

            // Retrieve user information from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(nickName);
            if (userDetails == null) {
                logger.error("User with nickname {} not found", nickName);
                throw new CommonException(404, "User not found");
            }


            // Generate and save token in Redis
            String token = tokenService.generateToken(userDetails);
            logger.info("Generated token for user {}: {}", nickName, token);

            redisTemplate.opsForValue().set(nickName, token);

            System.out.println("User " + nickName + " is now online with token: " + token);

            // Return success response with the generated token
            return ResponseEntity.ok("Login successful. Token: " + token);

        } catch (CommonException e) {
            logger.error("Error during login: {}", e.getMessage());
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error during login: {}", e.getMessage());
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
}
