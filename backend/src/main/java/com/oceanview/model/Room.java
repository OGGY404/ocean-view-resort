package com.oceanview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a hotel room.
 * Stored in the 'rooms' collection in MongoDB.
 *
 * Design Pattern: Uses an Enumeration (RoomType) for type safety —
 * prevents invalid room types being entered.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rooms")
public class Room {

    @Id
    private String id;

    private String roomNumber;    // e.g. "101", "202"
    private RoomType roomType;    // SINGLE, DOUBLE, or SUITE
    private double pricePerNight; // Cost per night in LKR
    private boolean available;    // true = can be booked

    /**
     * Enumeration for room types.
     * Using enum instead of String prevents typos and invalid values.
     * This demonstrates type safety — a key OOP concept.
     */
    public enum RoomType {
        SINGLE,   // 1 bed
        DOUBLE,   // 2 beds
        SUITE     // luxury room
    }
}
