package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserKeysDTO;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepository repository;

    @PostMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserKeysDTO> authentication(@RequestBody @Valid UserAuthDTO model){
        service.authenticate(model);
        return ResponseEntity.ok(service.generateUserKeys(model.email()));
    }

    @PostMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<String> active(){

        return ResponseEntity.ok(null);
    }


}
