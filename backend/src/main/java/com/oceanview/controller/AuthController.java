package com.oceanview.controller;

import com.oceanview.dto.LoginRequest;
import com.oceanview.dto.LoginResponse;
import com.oceanview.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication.
 *
 * Endpoints:
 * POST /api/auth/login  → Login and receive JWT token
 *
 * @RestController = @Controller + @ResponseBody (returns JSON automatically)
 * @RequestMapping sets the base URL path for all methods in this class
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Login endpoint.
     * Frontend sends: { "username": "admin", "password": "admin123" }
     * Returns: { "token": "eyJ...", "username": "admin", "role": "ROLE_ADMIN" }
     *
     * @Valid triggers the validation annotations on LoginRequest
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
