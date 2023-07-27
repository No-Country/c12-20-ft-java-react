package c1220ftjavareact.gym.active.controller;

import c1220ftjavareact.gym.active.dto.ActiveUserDTO;
import c1220ftjavareact.gym.active.service.IActiveUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/active")
public class ActiveUserController {
    private final IActiveUserService iActiveUserService;

    public ActiveUserController(IActiveUserService iActiveUserService) {
        this.iActiveUserService = iActiveUserService;
    }

    @PostMapping(value = "/send")
    public ResponseEntity<ActiveUserDTO> sendActiveUser(@RequestBody ActiveUserDTO activeUserDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iActiveUserService.sendActiveUser(activeUserDTO));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ActiveUserDTO> obtainActiveUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iActiveUserService.obtainActiveUser(id));
    }
}
