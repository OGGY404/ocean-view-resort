package com.oceanview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for creating a new reservation.
 * Frontend sends this to POST /api/reservations
 *
 * Validation annotations ensure invalid data never reaches the database.
 */
@Data
public class ReservationRequest {

    @NotBlank(message = "Guest name is required")
    private String guestName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;

    private String email;

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;
}
