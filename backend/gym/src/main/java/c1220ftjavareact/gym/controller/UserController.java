package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.UserUpdateDTO;
import c1220ftjavareact.gym.security.service.AuthService;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;
    private final AuthService authService;

    /**
     *
     * @param id ID del usuario
     * @Authorization Si necesita
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/admins/{id}")
    public HttpEntity<Void> deletEmployee(@PathVariable("id") String id){
        this.service.userLogicalDeleteById(id, "EMPLOYEE");
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *
     * @param updateUser Token JWT del usuario
     * @Authroization No necesita
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER','EMPLOYEE')")
    @PutMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, String>> updateUser(
            @PathVariable("id") String id,
            @RequestBody UserUpdateDTO updateUser
    ) {
        var user =this.service.updateUser(updateUser, id);

        var response = ResponseEntity.noContent();
        if(StringUtils.hasText(updateUser.email())){
            var token = this.authService.generateToken(user);

            return ResponseEntity.ok(Map.of("Authorize-update", "Bearer "+token));
        }
        return ResponseEntity.noContent().build();
    }
}
