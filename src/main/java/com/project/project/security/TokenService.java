package com.project.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final String SECRET_KEY = "mySecretKey"; // Load this securely

    private Set<String> invalidatedTokens = new HashSet<>();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean invalidateToken(String token) {
        // Add the token to the set of invalidated tokens
        return invalidatedTokens.add(token);
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiry
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        redisTemplate.opsForValue().set(token, userDetails.getUsername(), 10, TimeUnit.HOURS);

        return token;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        System.out.println("Validating token for user: " + username);
        boolean isExpired = isTokenExpired(token);
        System.out.println("Is token expired? " + isExpired);
        return (username.equals(userDetails.getUsername()) && !isExpired);
    }


    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
