package com.oceanview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Represents a staff/admin user who can log into the system.
 * Stored in the 'users' collection in MongoDB.
 *
 * Design Pattern: This acts as the Entity in the Repository Pattern.
 */
@Data               // Lombok: auto-generates getters, setters, toString, equals
@NoArgsConstructor  // Lombok: generates empty constructor
@AllArgsConstructor // Lombok: generates constructor with all fields
@Document(collection = "users")  // Maps this class to MongoDB 'users' collection
public class User {

    @Id  // MongoDB primary key (_id field)
    private String id;

    @Indexed(unique = true)  // Ensures no two users share the same username
    private String username;

    private String password;  // Stored as BCrypt hash — never plain text

    private String role;  // "ROLE_ADMIN" or "ROLE_STAFF"
}
