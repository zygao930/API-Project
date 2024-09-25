package com.project.project.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This service is responsible for generating, validating, and managing JSON Web Tokens (JWTs)
 * for user authentication and authorization.
 */
@Service
public class TokenService {

    private static final String SECRET_KEY = "mySecretKey"; // Load this securely

    private final Set<String> invalidatedTokens = new HashSet<>(); // Set to store invalidated tokens

    @Autowired
    private RedisTemplate<String, String> redisTemplate; // RedisTemplate for token storage

    /**
     * Invalidates the specified token by adding it to the set of invalidated tokens.
     *
     * @param token The JWT to invalidate.
     * @return true if the token was successfully added to the invalidated set; false if it was already present.
     */
    public boolean invalidateToken(String token) {
        return invalidatedTokens.add(token);
    }

    /**
     * Generates a JWT for the specified user details.
     *
     * @param userDetails The user details for which the token is to be generated.
     * @return The generated JWT as a string.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiry
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // Store the token in Redis with a 10-hour expiration
        redisTemplate.opsForValue().set(token, userDetails.getUsername(), 10, TimeUnit.HOURS);

        return token;
    }

    /**
     * Validates the specified token against the user details.
     *
     * @param token       The JWT to validate.
     * @param userDetails The user details to check against.
     * @return true if the token is valid; false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        System.out.println("Validating token for user: " + username);
        boolean isExpired = isTokenExpired(token);
        System.out.println("Is token expired? " + isExpired);
        return (username.equals(userDetails.getUsername()) && !isExpired);
    }

    /**
     * Extracts the username from the specified token.
     *
     * @param token The JWT from which to extract the username.
     * @return The username contained in the token.
     */
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Checks if the specified token is expired.
     *
     * @param token The JWT to check for expiration.
     * @return true if the token is expired; false otherwise.
     */
    public Boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
