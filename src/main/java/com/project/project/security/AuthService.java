package com.project.project.security;

import com.project.project.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static com.project.project.security.RSAEncrypt.decrypt;

@Service
public class AuthService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    private PrivateKey getPrivateKey() throws Exception {
        String privateKeyBase64 = System.getenv("RSA_PRIVATE_KEY");

        if (privateKeyBase64 == null || privateKeyBase64.isEmpty()) {
            throw new CommonException(500, "Private key not configured"); // Internal server error
        }
        System.out.println("Private Key Base64: " + privateKeyBase64); // Debugging purpose

        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyBase64);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            // Log the exception and rethrow with more context
            System.err.println("Error while loading private key: " + e.getMessage());
            throw new CommonException(500, "Error loading private key");
        }
    }

    public ResponseEntity<String> login(String username, String encryptedPassword, String captcha, String captchaKey) {
        try {
            // 1. Validate the captcha from Redis
            String storedCaptcha = redisTemplate.opsForValue().get(captchaKey);
            if (storedCaptcha == null) {
                throw new CommonException(300, "验证码已过期"); // "Captcha error or expired"
            }

            if (!storedCaptcha.equals(captcha)) {
                throw new CommonException(301, "验证码错误"); // "Captcha incorrect"
            }

            // 2. Decrypt the password using the RSA private key
            PrivateKey privateKey = getPrivateKey();
            String decryptedPassword = RSAEncrypt.decrypt(encryptedPassword, privateKey);

            // 3. Retrieve user information using UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 4. Validate the decrypted password with the stored password
            if (!passwordEncoder.matches(decryptedPassword, userDetails.getPassword())) {
                throw new CommonException(401, "Invalid username or password"); // "Username or password incorrect"
            }

            // 5. Check if the user is already logged in by checking Redis
            String existingToken = redisTemplate.opsForValue().get("auth:token:" + username);
            if (existingToken != null) {
                // If user is already logged in, invalidate the old session
                redisTemplate.delete("auth:token:" + username);
                redisTemplate.delete("auth:user:" + existingToken);
            }

            // 6. Generate a new token
            String token = tokenService.generateToken(userDetails);

            // 7. Store user session and token in Redis
            redisTemplate.opsForValue().set("auth:token:" + username, token);
            redisTemplate.opsForValue().set("auth:user:" + token, username);

            // Optional: Set an expiry time for the Redis entries
            redisTemplate.expire("auth:token:" + username, 1, java.util.concurrent.TimeUnit.HOURS);
            redisTemplate.expire("auth:user:" + token, 1, java.util.concurrent.TimeUnit.HOURS);

            // 8. Return the token and user info to the client
            return ResponseEntity.ok("Login successful. Token: " + token);
        } catch (CommonException e) {
            // Handle specific application exceptions
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
}
