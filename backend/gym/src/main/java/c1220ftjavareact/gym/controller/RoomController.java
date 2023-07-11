package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.RoomDto;
import c1220ftjavareact.gym.service.interfaces.IRoomService;
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
    public ResponseEntity<RoomDto> create(@RequestBody RoomDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.create(roomDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") long id) {
        this.iroomService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable("id") long id, @RequestBody RoomDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.updateRoom(id, roomDto));
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllDtoRoom() {
        return ResponseEntity.status(HttpStatus.OK).body(this.iroomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomDto(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iroomService.getRoomDtoById(id));
    }
}
