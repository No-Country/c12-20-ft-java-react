package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final UserService service;

    @PostMapping
    public HttpEntity<Void> registerClient(@Valid @RequestBody UserSaveDTO userDTO){
        service.registerClient(userDTO);

        return ResponseEntity.created(URI.create("/api/v1/clients")).build();
    }
}
