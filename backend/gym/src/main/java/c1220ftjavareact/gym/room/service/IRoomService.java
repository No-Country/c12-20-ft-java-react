package c1220ftjavareact.gym.room.service;

import c1220ftjavareact.gym.room.dto.RoomSaveDto;
import c1220ftjavareact.gym.room.dto.RoomWithIdDto;
import c1220ftjavareact.gym.room.entity.Room;

import java.util.List;

public interface IRoomService {
    public RoomSaveDto create(RoomSaveDto roomSaveDto);

    public RoomWithIdDto delete(Long id);

    public RoomSaveDto updateRoom(Long id, RoomSaveDto roomSaveDto);

    public List<RoomWithIdDto> getAllRooms();

    public Room getRoomById(Long id);

    public RoomWithIdDto getRoomDtoById(Long id);

}
