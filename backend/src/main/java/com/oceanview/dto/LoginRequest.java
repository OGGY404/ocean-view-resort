package com.oceanview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO Pattern: Data Transfer Object for login requests.
 * The frontend sends this JSON body to POST /api/auth/login
 *
 * @NotBlank is validation — returns 400 error if fields are empty.
 * This implements the "validation mechanisms" required by Task B.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
