package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<String> authentication(){


        return ResponseEntity.ok(null);
    }

    @PostMapping(name = "active", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<String> active(){

        return ResponseEntity.ok(null);
    }


}
