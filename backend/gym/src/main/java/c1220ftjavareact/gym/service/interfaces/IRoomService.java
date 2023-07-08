package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.RoomInDto;
import c1220ftjavareact.gym.repository.entity.Room;

import java.util.List;
import java.util.Optional;

public interface IRoomService {
    public Room create(RoomInDto roomInDto);

    public void delete(Long id);

    public Room updateRoom(Long id, Room room);

    public List<Room> getAllRooms();

    public Room getRoomById(Long id);

}
