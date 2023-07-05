package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.RoomInDto;
import c1220ftjavareact.gym.repository.entity.Room;

public interface IRoomService {
    Room create(RoomInDto roomInDto);
}
