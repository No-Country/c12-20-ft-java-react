package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<Void> resgisterUser(){

        return ResponseEntity.created(URI.create("/api/v1/admins")).build();
    }
}
