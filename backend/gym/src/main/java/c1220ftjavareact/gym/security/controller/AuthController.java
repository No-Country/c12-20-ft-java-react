package c1220ftjavareact.gym.security.controller;

import c1220ftjavareact.gym.domain.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.events.event.UserCreatedEvent;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import c1220ftjavareact.gym.security.jwt.JwtService;
import c1220ftjavareact.gym.security.service.AuthService;
import c1220ftjavareact.gym.service.email.EmployeeCreatedStrategy;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService service;
    @Qualifier("spring")
    private final AuthService springAuth;
    @Qualifier("google")
    private final AuthService googleAuth;
    private final JwtService<UserEntity> jwtService;
    private final UserMapperBeans userMapper;
    private final ApplicationEventPublisher publisher;

    /**
     * Endpoint para realizar el registro deL ADMIN (solo se crea una vez, datos por default)
     *
     * @Authorization No necesita
     */
    @PostMapping(value = "/admins/create")
    public HttpEntity<Void> registerAdmin() {
        var entity = userMapper.adminUser().map("owner1234");

        service.saveAdmin(userMapper.userEntityToUserSave().map(entity));

        return ResponseEntity.created(URI.create("/api/v1/admins")).build();
    }

    /**
     * Endpoint para realizar el registro de un cliente
     *
     * @param userDTO DTO con los datos que se guardaran del cliente
     * @Authorization No necesita
     */
    @PostMapping("/customers")
    public HttpEntity<Void> registerCustomer(@Valid @RequestBody UserSaveDTO userDTO) {

        this.service.assertEmailIsNotRegistered(userDTO.email());
        this.service.saveUser(userDTO, "CUSTOMER");

        return ResponseEntity.created(URI.create("/api/v1/customers")).build();
    }

    /**
     * Endpoint para realizar el registro de un empleado
     *
     * @param employeeDTO DTO con los datos que se guardaran del empleado
     *
     * @Authorization Si necesita Token y que el rol del usuario sea ADMIN
     */
    @PostMapping("/admins")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<Void> registerEmployee(@Valid @RequestBody EmployeeSaveDTO employeeDTO) {
        this.service.assertEmailIsNotRegistered(employeeDTO.email());
        var userDTO = this.userMapper.employeeSaveToUserSave().map(employeeDTO);

        service.saveUser(userDTO, "EMPLOYEE");

        publisher.publishEvent(new UserCreatedEvent(
                this,
                userDTO.email(),
                userDTO.name(),
                userDTO.lastname(),
                userDTO.password(),
                new EmployeeCreatedStrategy())
        );

        return ResponseEntity.created(URI.create("/api/v1/admins")).build();
    }

    /**
     * Endpoint para realizar el Login del usuario
     *
     * @param model Modelo con las credenciales del usuario
     * @Authroization No necesita
     */
    @PostMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, Object>> authentication(@RequestBody @Valid UserAuthDTO model) {
        this.springAuth.authenticate(model.email(), model.password());
        var user = this.service.findLoginInfo(model.email());
        var token = this.jwtService.generateToken(userMapper.userProjectionToUserEntity().map(user));

        return ResponseEntity.ok(Map.of(
                "user", user,
                "token", token
        ));
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *
     * @param oldToken Token JWT del usuario
     * @Authroization No necesita
     */
    @PostMapping(value = "/update-session", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, Object>> active(@RequestHeader("Authorization") String oldToken) {
        var email = this.jwtService.extractSubject(oldToken.substring(7));
        var user = this.service.findUserByEmail(email);
        var token = this.jwtService.generateToken(userMapper.userToUserEntity().map(user));

        return ResponseEntity.ok(Map.of(
                "user", user,
                "token", token
        ));
    }
}
