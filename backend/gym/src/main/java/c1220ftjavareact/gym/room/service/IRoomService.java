package c1220ftjavareact.gym.room.service;

import c1220ftjavareact.gym.room.dto.RoomDto;
import c1220ftjavareact.gym.room.entity.Room;

import java.util.List;

public interface IRoomService {
    public RoomDto create(RoomDto roomDto);

    public void delete(Long id);

    public RoomDto updateRoom(Long id, RoomDto roomDto);

    public List<RoomDto> getAllRooms();

    public Room getRoomById(Long id);

    public RoomDto getRoomDtoById(Long id);

}
