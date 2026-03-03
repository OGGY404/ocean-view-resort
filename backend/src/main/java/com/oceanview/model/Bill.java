package com.oceanview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents the bill for a reservation.
 * Note: Bill is NOT a separate MongoDB document — it is embedded
 * INSIDE a Reservation document. This is called Composition.
 *
 * Design Decision: Bill cannot exist without a Reservation.
 * In UML this is shown as a filled diamond (◆) — Composition.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    private long numberOfNights;    // checkout date - checkin date
    private double pricePerNight;   // copied from Room at time of booking
    private double totalAmount;     // numberOfNights * pricePerNight
    private boolean paid;           // has the guest paid?
}
