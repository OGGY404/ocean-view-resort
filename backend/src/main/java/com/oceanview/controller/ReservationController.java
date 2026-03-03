package com.oceanview.controller;

import com.oceanview.dto.ReservationRequest;
import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for reservation management.
 *
 * All endpoints require a valid JWT token (enforced by SecurityConfig).
 *
 * Endpoints:
 * POST   /api/reservations          → Add new reservation
 * GET    /api/reservations          → Get all reservations
 * GET    /api/reservations/{number} → Get specific reservation
 * GET    /api/reservations/search?name=John → Search by guest name
 * PUT    /api/reservations/{number}/status  → Update status
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.addReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{number}")
    public ResponseEntity<Reservation> getReservation(@PathVariable String number) {
        return ResponseEntity.ok(reservationService.getByReservationNumber(number));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Reservation>> searchByGuestName(@RequestParam String name) {
        return ResponseEntity.ok(reservationService.searchByGuestName(name));
    }

    @PutMapping("/{number}/status")
    public ResponseEntity<Reservation> updateStatus(
            @PathVariable String number,
            @RequestParam Reservation.ReservationStatus status) {
        return ResponseEntity.ok(reservationService.updateStatus(number, status));
    }
}
