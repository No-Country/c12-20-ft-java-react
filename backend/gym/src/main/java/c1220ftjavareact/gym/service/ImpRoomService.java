package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.RoomInDto;
import c1220ftjavareact.gym.domain.mapper.IMapperRoom;
import c1220ftjavareact.gym.repository.RoomRepository;
import c1220ftjavareact.gym.repository.entity.Room;
import c1220ftjavareact.gym.service.interfaces.IRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImpRoomService implements IRoomService {
    private final RoomRepository roomRepository;
    private final IMapperRoom<RoomInDto, Room> iMapperRoom;

    public ImpRoomService(RoomRepository roomRepository, IMapperRoom<RoomInDto, Room> iMapperRoom) {
        this.roomRepository = roomRepository;
        this.iMapperRoom = iMapperRoom;
    }

    @Transactional
    @Override
    public Room create(RoomInDto roomInDto) {
        return this.roomRepository.save(iMapperRoom.map(roomInDto));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Room updateRoom(Long id, Room room) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Rooms null"));
    }
}
