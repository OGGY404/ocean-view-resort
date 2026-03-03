package com.oceanview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a room reservation — the core entity of the system.
 * Stored in the 'reservations' collection in MongoDB.
 *
 * Relationships shown in Class Diagram:
 * - Reservation "has a" Guest (Association, multiplicity 1..1)
 * - Reservation "has a" Room  (Association, multiplicity 1..1)
 * - Reservation "contains" Bill (Composition ◆ — bill dies with reservation)
 *
 * Design Pattern: Uses ReservationStatus enum for type safety.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reservations")
public class Reservation {

    @Id
    private String id;

    private String reservationNumber;  // Unique e.g. "RES-2025-001"

    // Embedded objects (stored inside the reservation document)
    private Guest guest;               // Guest details
    private Room room;                 // Room details at time of booking
    private Bill bill;                 // Calculated bill (Composition)

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;   // When the booking was made

    private ReservationStatus status;  // CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED

    /**
     * Enumeration for reservation status.
     * Represents the lifecycle of a booking.
     */
    public enum ReservationStatus {
        CONFIRMED,    // Booking made, guest not arrived yet
        CHECKED_IN,   // Guest has arrived
        CHECKED_OUT,  // Guest has left
        CANCELLED     // Booking was cancelled
    }
}
