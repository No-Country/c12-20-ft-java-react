package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserKeysDTO;
import c1220ftjavareact.gym.service.interfaces.UpdatePasswordService;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UpdatePasswordService passwordService;

    @PostMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserKeysDTO> authentication(@RequestBody @Valid UserAuthDTO model) {
        service.authenticate(model);
        return ResponseEntity.ok(service.generateUserKeys(model.email()));
    }

    @PostMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserKeysDTO> active(@RequestHeader("Authorization") String token) {
        var updateUserKeys = service.updateUserKeys(token);

        return ResponseEntity.ok(updateUserKeys);
    }


}
