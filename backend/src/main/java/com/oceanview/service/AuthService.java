package com.oceanview.service;

import com.oceanview.dto.LoginRequest;
import com.oceanview.dto.LoginResponse;
import com.oceanview.model.User;
import com.oceanview.repository.UserRepository;
import com.oceanview.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service Layer: Handles all authentication business logic.
 *
 * What happens during login:
 * 1. AuthenticationManager verifies username + password against MongoDB
 * 2. JwtUtil creates a signed token
 * 3. Token + user info returned to the frontend
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user and returns a JWT token.
     */
    public LoginResponse login(LoginRequest request) {
        // This throws an exception if credentials are wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // If we reach here, credentials are correct
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new LoginResponse(token, user.getUsername(), user.getRole(), "Login successful");
    }

    /**
     * Creates the initial admin user if no users exist.
     * Called at application startup via DataInitializer.
     */
    public void createDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User(null, "admin",
                    passwordEncoder.encode("admin123"), "ROLE_ADMIN");
            userRepository.save(admin);

            User staff = new User(null, "staff",
                    passwordEncoder.encode("staff123"), "ROLE_STAFF");
            userRepository.save(staff);
        }
    }
}
