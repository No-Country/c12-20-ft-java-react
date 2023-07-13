package c1220ftjavareact.gym.room.service;

import c1220ftjavareact.gym.room.dto.RoomDto;
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
    public RoomDto create(RoomDto roomDto) {
        Room room = this.modelMapper.map(roomDto, Room.class);
        this.roomRepository.save(room);
        return this.modelMapper.map(room, RoomDto.class);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.roomRepository.deleteById(id);
    }

    @Transactional
    @Override
    public RoomDto updateRoom(Long id, RoomDto roomDto) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        if (room != null) {
            this.modelMapper.map(roomDto, room);
            Room updateRoom = this.roomRepository.save(room);
            return this.modelMapper.map(room, RoomDto.class);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = this.roomRepository.findAll();
        List<RoomDto> roomsDto = new ArrayList<>();

        for (Room room : rooms) {
            RoomDto roomDto = this.modelMapper.map(room, RoomDto.class);
            roomsDto.add(roomDto);
        }

        return roomsDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Room getRoomById(Long id) {
        return this.roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room null"));
    }

    @Transactional(readOnly = true)
    @Override
    public RoomDto getRoomDtoById(Long id) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        return this.modelMapper.map(room, RoomDto.class);
    }
}
