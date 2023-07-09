package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.RoomInDto;
import c1220ftjavareact.gym.repository.entity.Room;
import c1220ftjavareact.gym.service.interfaces.IRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private IRoomService iroomService;

    public RoomController(IRoomService iroomService) {
        this.iroomService = iroomService;
    }

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody RoomInDto roomInDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iroomService.create(roomInDto));
    }
}
