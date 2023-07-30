package c1220ftjavareact.gym.room.service;

import c1220ftjavareact.gym.room.dto.RoomSaveDto;
import c1220ftjavareact.gym.room.dto.RoomWithIdDto;
import c1220ftjavareact.gym.room.entity.Room;
import c1220ftjavareact.gym.room.exception.RoomException;
import c1220ftjavareact.gym.room.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImpRoomService implements IRoomService {
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public RoomSaveDto create(RoomSaveDto roomSaveDto) {
        Room room = this.modelMapper.map(roomSaveDto, Room.class);
        if (!StringUtils.hasText(roomSaveDto.getName())) {
            throw new RoomException("The name is empty", HttpStatus.BAD_REQUEST);
        }

        if (roomSaveDto.getMaxCapacity() <= 0) {
            throw new RoomException("The maximum capacity must be greater than 0", HttpStatus.BAD_REQUEST);
        }

        this.roomRepository.save(room);
        return this.modelMapper.map(room, RoomSaveDto.class);
    }

    @Transactional
    @Override
    public RoomWithIdDto delete(Long id) {
        Room room = this.roomRepository.findRoomFalse(id);
        if (room.isDeleted()) {
            throw new RoomException("The room is not found", HttpStatus.NOT_FOUND);
        }
        RoomWithIdDto roomWithIdDto = this.modelMapper.map(room, RoomWithIdDto.class);
        this.roomRepository.deleteRoom(id);

        return roomWithIdDto;
    }

    @Transactional
    @Override
    public RoomSaveDto updateRoom(Long id, RoomSaveDto roomSaveDto) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        if (!StringUtils.hasText(roomSaveDto.getName())) {
            throw new RoomException("The name cannot empy", HttpStatus.BAD_REQUEST);
        }

        if (roomSaveDto.getMaxCapacity() <= 0) {
            throw new RoomException("The maximum capacity must be greater than 0", HttpStatus.BAD_REQUEST);
        }

        if (room.isDeleted() || room == null) {
            throw new RoomException("The room is not found", HttpStatus.NOT_FOUND);
        } else {
            this.modelMapper.map(roomSaveDto, room);
            Room updateRoom = this.roomRepository.save(room);
            return this.modelMapper.map(updateRoom, RoomSaveDto.class);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public RoomWithIdDto getRoomDtoById(Long id) {
        Room room = this.roomRepository.findRoomFalse(id);
        if (room == null) {
            throw new RoomException("The room is not found", HttpStatus.NOT_FOUND);
        }
        return this.modelMapper.map(room, RoomWithIdDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomWithIdDto> getAllRooms() {
        List<Room> rooms = this.roomRepository.findAllRoomByDeletedFalse();
        List<RoomWithIdDto> roomWithIdDtos = new ArrayList<>();

        for (Room room : rooms) {
            RoomWithIdDto roomWithIdDto = this.modelMapper.map(room, RoomWithIdDto.class);
            roomWithIdDtos.add(roomWithIdDto);
        }

        return roomWithIdDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public Room getRoomById(Long id) {
        return this.roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room null"));
    }

}
