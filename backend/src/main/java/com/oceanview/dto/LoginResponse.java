package com.oceanview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO Pattern: What the server sends BACK after successful login.
 * Contains the JWT token the frontend stores and uses for future requests.
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;    // JWT token
    private String username;
    private String role;     // "ROLE_ADMIN" or "ROLE_STAFF"
    private String message;  // "Login successful"
}
