package c1220ftjavareact.gym.room.repository;

import c1220ftjavareact.gym.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllRoomByDeletedFalse();

    @Query(value = "SELECT * FROM room WHERE deleted = false AND id = :id", nativeQuery = true)
    Room findRoomFalse(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE room SET deleted = true WHERE id = :id", nativeQuery = true)
    void deleteRoom(@Param("id") Long id);
}
