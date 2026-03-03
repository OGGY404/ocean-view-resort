package com.oceanview.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Health check endpoint — Version 2 feature.
 * Allows monitoring tools to verify the API is running.
 * Added in v2 of the system.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "application", "Ocean View Resort API",
            "timestamp", LocalDateTime.now().toString(),
            "version", "2.0"
        ));
    }
}
