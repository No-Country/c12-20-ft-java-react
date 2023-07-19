package c1220ftjavareact.gym.room.service;

<<<<<<< HEAD
import c1220ftjavareact.gym.room.dto.RoomSaveDto;
import c1220ftjavareact.gym.room.dto.RoomWithIdDto;
=======
import c1220ftjavareact.gym.room.dto.RoomDto;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
import c1220ftjavareact.gym.room.entity.Room;

import java.util.List;

public interface IRoomService {
<<<<<<< HEAD
    public RoomSaveDto create(RoomSaveDto roomSaveDto);

    public void delete(Long id);

    public RoomSaveDto updateRoom(Long id, RoomSaveDto roomSaveDto);

    public List<RoomWithIdDto> getAllRooms();

    public Room getRoomById(Long id);

    public RoomWithIdDto getRoomDtoById(Long id);
=======
    public RoomDto create(RoomDto roomDto);

    public void delete(Long id);

    public RoomDto updateRoom(Long id, RoomDto roomDto);

    public List<RoomDto> getAllRooms();

    public Room getRoomById(Long id);

    public RoomDto getRoomDtoById(Long id);
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34

}
