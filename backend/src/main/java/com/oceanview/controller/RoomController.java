package com.oceanview.controller;

import com.oceanview.model.Room;
import com.oceanview.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for room management.
 *
 * Endpoints:
 * GET  /api/rooms           → All rooms
 * GET  /api/rooms/available → Available rooms only
 * POST /api/rooms           → Add a new room (admin only in practice)
 */
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        return ResponseEntity.ok(roomService.addRoom(room));
    }

    @PutMapping("/{roomNumber}/price")
    public ResponseEntity<Room> updatePrice(@PathVariable String roomNumber,
                                            @RequestParam double price) {
        return ResponseEntity.ok(roomService.updateRoomPrice(roomNumber, price));
    }

    @DeleteMapping("/{roomNumber}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomNumber) {
        roomService.deleteRoom(roomNumber);
        return ResponseEntity.ok().build();
    }
}
