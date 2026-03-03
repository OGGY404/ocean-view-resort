package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for reports — satisfies Task B requirement:
 * "Come up with a suitable set of reports which add more value to your system"
 *
 * Endpoints:
 * GET /api/reports/booking-history?start=2025-01-01&end=2025-12-31
 *     → All reservations within a date range
 *
 * GET /api/reports/status?status=CONFIRMED
 *     → All reservations with a given status
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReservationService reservationService;

    /**
     * Booking History Report: reservations between two dates.
     * Useful for management to see monthly occupancy.
     */
    @GetMapping("/booking-history")
    public ResponseEntity<List<Reservation>> getBookingHistory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(reservationService.getBookingReport(start, end));
    }

    /**
     * All reservations (for dashboard overview).
     */
    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
}
