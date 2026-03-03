package com.oceanview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a hotel guest.
 * Stored in the 'guests' collection in MongoDB.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "guests")
public class Guest {

    @Id
    private String id;

    private String guestId;       // Unique guest code e.g. "G001"
    private String name;          // Full name
    private String address;       // Home address
    private String contactNumber; // Phone number
    private String email;         // Email address
}
