package c1220ftjavareact.gym.user.controller;

import c1220ftjavareact.gym.user.dto.EmployeeDTO;
import c1220ftjavareact.gym.user.dto.UserUpdateDTO;
import c1220ftjavareact.gym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    /**
     * Cambiar el estado de deleted de un Empleado
     *
     * @Authorization Si necesita
     */
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/employees/state")
    public HttpEntity<Void> changeStateUser(@RequestBody Set<Long> employeeIds) {

        this.service.changeDeletedStateUser(employeeIds);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *
     * @param updateUser Token JWT del usuario
     * @Authroization No necesita
     */
    //@PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER','EMPLOYEE')")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, String>> updateUser(
            @PathVariable("id") String id,
            @RequestBody UserUpdateDTO updateUser
    ) {

        this.service.updateUser(updateUser, id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<Set<EmployeeDTO>> finddEmployees() {

        return ResponseEntity.ok(this.service.findAllEmployees());
    }

    @GetMapping("/{id}/subscriptions")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<Set<String>> findActiveSubscriptions(@PathVariable("id") String id) {

        return ResponseEntity.ok(this.service.findActiveActivity(id));
    }
}
