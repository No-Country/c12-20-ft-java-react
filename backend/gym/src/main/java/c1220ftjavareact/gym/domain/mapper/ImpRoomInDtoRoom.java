package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.dto.RoomInDto;
import c1220ftjavareact.gym.repository.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class ImpRoomInDtoRoom implements IMapperRoom<RoomInDto, Room> {
    @Override
    public Room map(RoomInDto in) {
        var room = new Room();
        room.setMax_capacity(in.getMax_capacity());
        return room;
    }
}
