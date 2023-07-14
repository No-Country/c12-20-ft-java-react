package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.RoomDto;
import c1220ftjavareact.gym.repository.entity.Room;

import java.util.List;

public interface IRoomService {
    public RoomDto create(RoomDto roomDto);

    public void delete(Long id);

    public RoomDto updateRoom(Long id, RoomDto roomDto);

    public List<RoomDto> getAllRooms();

    public Room getRoomById(Long id);

    public RoomDto getRoomDtoById(Long id);

}
