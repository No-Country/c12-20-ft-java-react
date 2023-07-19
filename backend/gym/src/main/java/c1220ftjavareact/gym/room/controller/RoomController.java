package c1220ftjavareact.gym.room.controller;

<<<<<<< HEAD
import c1220ftjavareact.gym.room.dto.RoomSaveDto;
import c1220ftjavareact.gym.room.dto.RoomWithIdDto;
=======
import c1220ftjavareact.gym.room.dto.RoomDto;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
import c1220ftjavareact.gym.room.service.IRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private IRoomService iroomService;

    public RoomController(IRoomService iroomService) {
        this.iroomService = iroomService;
    }

    @PostMapping
<<<<<<< HEAD
    public ResponseEntity<RoomSaveDto> create(@RequestBody RoomSaveDto roomSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.create(roomSaveDto));
=======
    public ResponseEntity<RoomDto> create(@RequestBody RoomDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.create(roomDto));
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") long id) {
        this.iroomService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<RoomSaveDto> updateRoom(@PathVariable("id") long id, @RequestBody RoomSaveDto roomSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.updateRoom(id, roomSaveDto));
    }

    @GetMapping
    public ResponseEntity<List<RoomWithIdDto>> getAllDtoRoom() {
=======
    public ResponseEntity<RoomDto> updateRoom(@PathVariable("id") long id, @RequestBody RoomDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.updateRoom(id, roomDto));
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllDtoRoom() {
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return ResponseEntity.status(HttpStatus.OK).body(this.iroomService.getAllRooms());
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<RoomWithIdDto> getRoomDto(@PathVariable("id") Long id) {
=======
    public ResponseEntity<RoomDto> getRoomDto(@PathVariable("id") Long id) {
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return ResponseEntity.status(HttpStatus.OK).body(this.iroomService.getRoomDtoById(id));
    }
}
