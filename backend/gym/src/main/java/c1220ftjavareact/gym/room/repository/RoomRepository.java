package c1220ftjavareact.gym.room.repository;

import c1220ftjavareact.gym.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
