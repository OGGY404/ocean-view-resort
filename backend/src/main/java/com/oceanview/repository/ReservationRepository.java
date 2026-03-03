package com.oceanview.repository;

import com.oceanview.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Reservation documents in MongoDB.
 * Supports extra features: search by guest name, date range reports.
 */
@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    Optional<Reservation> findByReservationNumber(String reservationNumber);

    // Guest search feature — finds reservations by guest name (partial match)
    List<Reservation> findByGuestNameContainingIgnoreCase(String name);

    // Find all bookings for a specific room (for availability check)
    List<Reservation> findByRoomRoomNumber(String roomNumber);

    // Booking history report — filter by date range
    List<Reservation> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by status (e.g. all CONFIRMED bookings)
    List<Reservation> findByStatus(Reservation.ReservationStatus status);

    boolean existsByReservationNumber(String reservationNumber);
}
