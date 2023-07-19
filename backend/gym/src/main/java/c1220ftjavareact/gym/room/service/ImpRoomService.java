package c1220ftjavareact.gym.room.service;

<<<<<<< HEAD
import c1220ftjavareact.gym.room.dto.RoomSaveDto;
import c1220ftjavareact.gym.room.dto.RoomWithIdDto;
=======
import c1220ftjavareact.gym.room.dto.RoomDto;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
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
<<<<<<< HEAD
    public RoomSaveDto create(RoomSaveDto roomSaveDto) {
        Room room = this.modelMapper.map(roomSaveDto, Room.class);
        this.roomRepository.save(room);
        return this.modelMapper.map(room, RoomSaveDto.class);
=======
    public RoomDto create(RoomDto roomDto) {
        Room room = this.modelMapper.map(roomDto, Room.class);
        this.roomRepository.save(room);
        return this.modelMapper.map(room, RoomDto.class);
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.roomRepository.deleteById(id);
    }

    @Transactional
    @Override
<<<<<<< HEAD
    public RoomSaveDto updateRoom(Long id, RoomSaveDto roomSaveDto) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        if (room != null) {
            this.modelMapper.map(roomSaveDto, room);
            Room updateRoom = this.roomRepository.save(room);
            return this.modelMapper.map(room, RoomSaveDto.class);
=======
    public RoomDto updateRoom(Long id, RoomDto roomDto) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        if (room != null) {
            this.modelMapper.map(roomDto, room);
            Room updateRoom = this.roomRepository.save(room);
            return this.modelMapper.map(room, RoomDto.class);
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
<<<<<<< HEAD
    public List<RoomWithIdDto> getAllRooms() {
        List<Room> rooms = this.roomRepository.findAll();
        List<RoomWithIdDto> roomWithIdDtos = new ArrayList<>();

        for (Room room : rooms) {
            RoomWithIdDto roomWithIdDto = this.modelMapper.map(room, RoomWithIdDto.class);
            roomWithIdDtos.add(roomWithIdDto);
        }

        return roomWithIdDtos;
=======
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = this.roomRepository.findAll();
        List<RoomDto> roomsDto = new ArrayList<>();

        for (Room room : rooms) {
            RoomDto roomDto = this.modelMapper.map(room, RoomDto.class);
            roomsDto.add(roomDto);
        }

        return roomsDto;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }

    @Transactional(readOnly = true)
    @Override
    public Room getRoomById(Long id) {
        return this.roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room null"));
    }

    @Transactional(readOnly = true)
    @Override
<<<<<<< HEAD
    public RoomWithIdDto getRoomDtoById(Long id) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        return this.modelMapper.map(room, RoomWithIdDto.class);
=======
    public RoomDto getRoomDtoById(Long id) {
        Room room = this.roomRepository.findById(id).orElseThrow(null);
        return this.modelMapper.map(room, RoomDto.class);
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }
}
