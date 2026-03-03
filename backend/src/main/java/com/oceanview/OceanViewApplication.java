package com.oceanview;

import com.oceanview.service.AuthService;
import com.oceanview.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Ocean View Resort application.
 *
 * @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
 * This one annotation starts the entire Spring Boot application.
 *
 * CommandLineRunner: runs once after the application starts.
 * We use it to create default users and rooms in MongoDB.
 */
@SpringBootApplication
@RequiredArgsConstructor
public class OceanViewApplication implements CommandLineRunner {

    private final AuthService authService;
    private final RoomService roomService;

    public static void main(String[] args) {
        SpringApplication.run(OceanViewApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Ocean View Resort System Started!");
        System.out.println("  API running at: http://localhost:8080");
        System.out.println("  Default login: admin / admin123");
        System.out.println("==============================================");
    }

    @Override
    public void run(String... args) {
        // Seed default data on first run
        authService.createDefaultAdmin();
        roomService.createDefaultRooms();
    }
}
