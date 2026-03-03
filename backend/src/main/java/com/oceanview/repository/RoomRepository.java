package com.oceanview.repository;

import com.oceanview.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Room documents in MongoDB.
 */
@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

    Optional<Room> findByRoomNumber(String roomNumber);

    // Find all rooms that are currently available
    List<Room> findByAvailableTrue();

    // Find available rooms of a specific type
    List<Room> findByRoomTypeAndAvailableTrue(Room.RoomType roomType);

    boolean existsByRoomNumber(String roomNumber);
}
