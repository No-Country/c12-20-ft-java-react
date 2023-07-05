package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.repository.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
