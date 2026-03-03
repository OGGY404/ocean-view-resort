package com.oceanview;

import com.oceanview.dto.ReservationRequest;
import com.oceanview.model.Bill;
import com.oceanview.model.Guest;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.repository.ReservationRepository;
import com.oceanview.service.ReservationService;
import com.oceanview.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for ReservationService — Task C.
 *
 * What is Test-Driven Development (TDD)?
 * Write the test FIRST, then write the code to make it pass.
 * Red → Green → Refactor cycle.
 *
 * @ExtendWith(MockitoExtension.class) — enables Mockito mocking
 * @Mock — creates a fake/mock version of the dependency
 * @InjectMocks — creates the class under test with mocks injected
 *
 * Why mock? We test the SERVICE in isolation, without needing
 * a real MongoDB database running. Mockito simulates it.
 */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private ReservationService reservationService;

    private Room testRoom;
    private ReservationRequest validRequest;

    @BeforeEach
    void setUp() {
        // Arrange: create test data used across tests
        testRoom = new Room("room1", "101", Room.RoomType.SINGLE, 5000.0, true);

        validRequest = new ReservationRequest();
        validRequest.setGuestName("Kasun Perera");
        validRequest.setAddress("No. 1, Main St, Colombo");
        validRequest.setContactNumber("0771234567");
        validRequest.setEmail("kasun@email.com");
        validRequest.setRoomNumber("101");
        validRequest.setCheckInDate(LocalDate.of(2025, 6, 1));
        validRequest.setCheckOutDate(LocalDate.of(2025, 6, 5));
    }

    // ======================== TEST 1 ========================
    @Test
    void addReservation_ShouldCreateSuccessfully_WhenRoomAvailable() {
        // Arrange: mock what the dependencies will return
        when(roomService.getRoomByNumber("101")).thenReturn(testRoom);
        when(reservationRepository.findByRoomRoomNumber("101")).thenReturn(new ArrayList<>());
        when(reservationRepository.count()).thenReturn(0L);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act: call the method being tested
        Reservation result = reservationService.addReservation(validRequest);

        // Assert: verify the outcome
        assertNotNull(result);
        assertEquals("Kasun Perera", result.getGuest().getName());
        assertEquals("101", result.getRoom().getRoomNumber());
        assertEquals(Reservation.ReservationStatus.CONFIRMED, result.getStatus());
        assertEquals(4, result.getBill().getNumberOfNights()); // 4 nights
        assertEquals(20000.0, result.getBill().getTotalAmount()); // 4 x 5000
    }

    // ======================== TEST 2 ========================
    @Test
    void addReservation_ShouldThrowException_WhenRoomNotAvailable() {
        // Arrange: room is NOT available
        testRoom.setAvailable(false);
        when(roomService.getRoomByNumber("101")).thenReturn(testRoom);

        // Act + Assert: expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservationService.addReservation(validRequest));

        assertEquals("Room 101 is not available", exception.getMessage());
    }

    // ======================== TEST 3 ========================
    @Test
    void addReservation_ShouldThrowException_WhenCheckoutBeforeCheckin() {
        // Arrange: invalid dates
        validRequest.setCheckOutDate(LocalDate.of(2025, 5, 31)); // Before check-in

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservationService.addReservation(validRequest));

        assertEquals("Check-out date must be after check-in date", exception.getMessage());
    }

    // ======================== TEST 4 ========================
    @Test
    void addReservation_ShouldCalculateBillCorrectly() {
        // 3 nights in a suite at LKR 15,000 = LKR 45,000
        Room suite = new Room("room2", "301", Room.RoomType.SUITE, 15000.0, true);
        validRequest.setRoomNumber("301");
        validRequest.setCheckInDate(LocalDate.of(2025, 7, 10));
        validRequest.setCheckOutDate(LocalDate.of(2025, 7, 13)); // 3 nights

        when(roomService.getRoomByNumber("301")).thenReturn(suite);
        when(reservationRepository.findByRoomRoomNumber("301")).thenReturn(new ArrayList<>());
        when(reservationRepository.count()).thenReturn(5L);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        Reservation result = reservationService.addReservation(validRequest);

        assertEquals(3, result.getBill().getNumberOfNights());
        assertEquals(45000.0, result.getBill().getTotalAmount());
        assertFalse(result.getBill().isPaid()); // Unpaid by default
    }

    // ======================== TEST 5 ========================
    @Test
    void getByReservationNumber_ShouldReturnReservation_WhenExists() {
        // Arrange
        Reservation mockReservation = new Reservation();
        mockReservation.setReservationNumber("RES-2025-001");
        when(reservationRepository.findByReservationNumber("RES-2025-001"))
                .thenReturn(Optional.of(mockReservation));

        // Act
        Reservation result = reservationService.getByReservationNumber("RES-2025-001");

        // Assert
        assertEquals("RES-2025-001", result.getReservationNumber());
    }

    // ======================== TEST 6 ========================
    @Test
    void getByReservationNumber_ShouldThrowException_WhenNotFound() {
        when(reservationRepository.findByReservationNumber("INVALID"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> reservationService.getByReservationNumber("INVALID"));
    }
}
