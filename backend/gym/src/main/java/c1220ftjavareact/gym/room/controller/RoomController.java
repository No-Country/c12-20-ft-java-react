package c1220ftjavareact.gym.room.controller;

import c1220ftjavareact.gym.room.dto.RoomSaveDto;
import c1220ftjavareact.gym.room.dto.RoomWithIdDto;
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
    public ResponseEntity<RoomSaveDto> create(@RequestBody RoomSaveDto roomSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.create(roomSaveDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") long id) {
        this.iroomService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomSaveDto> updateRoom(@PathVariable("id") long id, @RequestBody RoomSaveDto roomSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.updateRoom(id, roomSaveDto));
    }

    @GetMapping
    public ResponseEntity<List<RoomWithIdDto>> getAllDtoRoom() {
        return ResponseEntity.status(HttpStatus.OK).body(this.iroomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomWithIdDto> getRoomDto(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iroomService.getRoomDtoById(id));
    }
}
