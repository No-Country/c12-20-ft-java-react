package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.RoomInDto;
import c1220ftjavareact.gym.domain.mapper.IMapperRoom;
import c1220ftjavareact.gym.repository.RoomRepository;
import c1220ftjavareact.gym.repository.entity.Room;
import c1220ftjavareact.gym.service.interfaces.IRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
