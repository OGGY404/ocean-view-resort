package com.oceanview.service;

import com.oceanview.model.Room;
import com.oceanview.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Layer: Business logic for room management.
 */
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailableTrue();
    }

    public Room getRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomNumber));
    }

    public Room addRoom(Room room) {
        if (roomRepository.existsByRoomNumber(room.getRoomNumber())) {
            throw new RuntimeException("Room number already exists: " + room.getRoomNumber());
        }
        room.setAvailable(true);
        return roomRepository.save(room);
    }

    public void setRoomAvailability(String roomNumber, boolean available) {
        Room room = getRoomByNumber(roomNumber);
        room.setAvailable(available);
        roomRepository.save(room);
    }

    public Room updateRoomPrice(String roomNumber, double newPrice) {
        Room room = getRoomByNumber(roomNumber);
        room.setPricePerNight(newPrice);
        return roomRepository.save(room);
    }

    public void deleteRoom(String roomNumber) {
        Room room = getRoomByNumber(roomNumber);
        roomRepository.delete(room);
    }

    /**
     * Seeds initial room data if no rooms exist.
     */
    public void createDefaultRooms() {
        if (roomRepository.count() == 0) {
            roomRepository.save(new Room(null, "101", Room.RoomType.SINGLE, 5000.0, true));
            roomRepository.save(new Room(null, "102", Room.RoomType.SINGLE, 5000.0, true));
            roomRepository.save(new Room(null, "201", Room.RoomType.DOUBLE, 8500.0, true));
            roomRepository.save(new Room(null, "202", Room.RoomType.DOUBLE, 8500.0, true));
            roomRepository.save(new Room(null, "301", Room.RoomType.SUITE,  15000.0, true));
            roomRepository.save(new Room(null, "302", Room.RoomType.SUITE,  15000.0, true));
        }
    }
}
