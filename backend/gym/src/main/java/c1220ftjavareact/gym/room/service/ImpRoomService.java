package c1220ftjavareact.gym.room.service;

import c1220ftjavareact.gym.room.dto.RoomSaveDto;
import c1220ftjavareact.gym.room.dto.RoomWithIdDto;
import c1220ftjavareact.gym.room.repository.RoomRepository;
import c1220ftjavareact.gym.room.entity.Room;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpRoomService implements IRoomService {
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public ImpRoomService(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public RoomSaveDto create(RoomSaveDto roomSaveDto) {
        Room room = this.modelMapper.map(roomSaveDto, Room.class);
        this.roomRepository.save(room);
        return this.modelMapper.map(room, RoomSaveDto.class);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.roomRepository.deleteById(id);
    }

    @Transactional
    @Override
    public RoomSaveDto updateRoom(Long id, RoomSaveDto roomSaveDto) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        if (room != null) {
            this.modelMapper.map(roomSaveDto, room);
            Room updateRoom = this.roomRepository.save(room);
            return this.modelMapper.map(room, RoomSaveDto.class);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomWithIdDto> getAllRooms() {
        List<Room> rooms = this.roomRepository.findAll();
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

    @Transactional(readOnly = true)
    @Override
    public RoomWithIdDto getRoomDtoById(Long id) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        return this.modelMapper.map(room, RoomWithIdDto.class);
    }
}
