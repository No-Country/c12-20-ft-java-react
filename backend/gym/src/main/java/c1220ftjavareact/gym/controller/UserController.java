package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.UserUpdateDTO;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    /**
     * Cambiar el estado de deleted de un Empleado
     *
     * @param id ID del usuario
     * @Authorization Si necesita
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/employees/{id}")
    public HttpEntity<Void> changeStateUser(@PathVariable("id") String id, @RequestParam(value = "deleted") Boolean deleted) {
        this.service.changeDeletedStateUser(id, "EMPLOYEE", deleted);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *
     * @param updateUser Token JWT del usuario
     * @Authroization No necesita
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER','EMPLOYEE')")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, String>> updateUser(
            @PathVariable("id") String id,
            @RequestBody UserUpdateDTO updateUser
    ) {

        this.service.updateUser(updateUser, id);

        return ResponseEntity.noContent().build();
    }
}
