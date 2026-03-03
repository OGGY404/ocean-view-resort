package com.oceanview.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for JWT (JSON Web Token) operations.
 *
 * What is JWT?
 * A JWT is a string with 3 parts separated by dots:
 *   header.payload.signature
 *   - Header: algorithm used (HS256)
 *   - Payload: username, role, expiry time
 *   - Signature: proves the token hasn't been tampered with
 *
 * Design Pattern: This is a Singleton — Spring creates one instance
 * and reuses it everywhere (all Spring @Component beans are Singletons).
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // 24 hours in milliseconds

    // Converts the secret string into a cryptographic key
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Creates a new JWT token for a user after successful login.
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)           // who the token is for
                .claim("role", role)         // embed the role in the token
                .issuedAt(new Date())        // when created
                .expiration(new Date(System.currentTimeMillis() + expiration)) // when it expires
                .signWith(getSigningKey())   // sign with our secret key
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Checks if a token is still valid (not expired, signature correct).
     */
    public boolean isTokenValid(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
