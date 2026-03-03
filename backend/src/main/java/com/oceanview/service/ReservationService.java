package com.oceanview.service;

import com.oceanview.dto.ReservationRequest;
import com.oceanview.model.Bill;
import com.oceanview.model.Guest;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service Layer: Core business logic for reservations.
 *
 * This is the most important service — it handles:
 * 1. Creating reservations (with availability check)
 * 2. Calculating bills automatically
 * 3. Generating unique reservation numbers
 * 4. Guest search
 * 5. Booking history reports
 *
 * Design Pattern: Facade Pattern — hides complex logic behind simple method calls.
 * The controller just calls addReservation() without knowing how billing works.
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;

    /**
     * Creates a new reservation.
     * Steps:
     * 1. Validate dates
     * 2. Check room availability
     * 3. Build Guest, Bill objects
     * 4. Generate reservation number
     * 5. Save to MongoDB
     * 6. Mark room as unavailable
     */
    public Reservation addReservation(ReservationRequest request) {
        // Step 1: Validate dates
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }

        // Step 2: Check room exists and is available
        Room room = roomService.getRoomByNumber(request.getRoomNumber());
        if (!room.isAvailable()) {
            throw new RuntimeException("Room " + request.getRoomNumber() + " is not available");
        }

        // Step 3: Check no overlapping reservations for this room
        checkRoomAvailabilityForDates(request.getRoomNumber(),
                request.getCheckInDate(), request.getCheckOutDate());

        // Step 4: Build the Guest object
        Guest guest = new Guest(null,
                generateGuestId(),
                request.getGuestName(),
                request.getAddress(),
                request.getContactNumber(),
                request.getEmail());

        // Step 5: Calculate the bill
        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        double total = nights * room.getPricePerNight();
        Bill bill = new Bill(nights, room.getPricePerNight(), total, false);

        // Step 6: Build and save the Reservation
        Reservation reservation = new Reservation(
                null,
                generateReservationNumber(),
                guest,
                room,
                bill,
                request.getCheckInDate(),
                request.getCheckOutDate(),
                LocalDateTime.now(),
                Reservation.ReservationStatus.CONFIRMED
        );

        Reservation saved = reservationRepository.save(reservation);

        // Step 7: Mark room as unavailable
        roomService.setRoomAvailability(request.getRoomNumber(), false);

        return saved;
    }

    public Reservation getByReservationNumber(String number) {
        return reservationRepository.findByReservationNumber(number)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + number));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Guest search feature — searches by name (partial match, case insensitive).
     */
    public List<Reservation> searchByGuestName(String name) {
        return reservationRepository.findByGuestNameContainingIgnoreCase(name);
    }

    /**
     * Booking history report — all reservations within a date range.
     */
    public List<Reservation> getBookingReport(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findByCheckInDateBetween(startDate, endDate);
    }

    public Reservation updateStatus(String number, Reservation.ReservationStatus status) {
        Reservation reservation = getByReservationNumber(number);
        reservation.setStatus(status);

        // If checked out, mark room as available again
        if (status == Reservation.ReservationStatus.CHECKED_OUT ||
            status == Reservation.ReservationStatus.CANCELLED) {
            roomService.setRoomAvailability(reservation.getRoom().getRoomNumber(), true);
        }

        return reservationRepository.save(reservation);
    }

    /**
     * Checks if a room has overlapping reservations for given dates.
     * This prevents double-booking — a key extra feature.
     */
    private void checkRoomAvailabilityForDates(String roomNumber,
                                                LocalDate checkIn, LocalDate checkOut) {
        List<Reservation> existing = reservationRepository.findByRoomRoomNumber(roomNumber);
        for (Reservation r : existing) {
            if (r.getStatus() == Reservation.ReservationStatus.CANCELLED) continue;
            // Overlap check: if new check-in is before existing checkout AND
            //                new checkout is after existing check-in → OVERLAP
            if (checkIn.isBefore(r.getCheckOutDate()) && checkOut.isAfter(r.getCheckInDate())) {
                throw new RuntimeException("Room is already booked for those dates");
            }
        }
    }

    private String generateReservationNumber() {
        long count = reservationRepository.count() + 1;
        return String.format("RES-%d-%03d", LocalDate.now().getYear(), count);
    }

    private String generateGuestId() {
        long count = reservationRepository.count() + 1;
        return String.format("G%04d", count);
    }
}
