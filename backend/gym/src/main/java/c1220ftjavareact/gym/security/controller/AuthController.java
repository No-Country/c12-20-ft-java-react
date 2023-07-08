package c1220ftjavareact.gym.security.controller;

import c1220ftjavareact.gym.domain.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.security.service.AuthService;
import c1220ftjavareact.gym.service.email.CustomerCreate;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final AuthService authService;
    private final UserMapperBeans userMapper;

    /**
     * Endpoint para realizar el registro deL ADMIN (solo se crea una vez, datos por default)
     *
     * @Authorization No necesita
     */
    @PostMapping(value = "/admins/create")
    public HttpEntity<Void> registerAdmin() {
        var entiy = userMapper.adminUser().map("owner1234");

        service.saveAdmin(userMapper.userEntityToUserSave().map(entiy), "ADMIN");

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
        if (!this.service.sendCreateMessage(userDTO, new CustomerCreate()))
            throw new UserSaveException("Error al enviar el email", "Espera y vuelve a intentarlo o revisa el formato del email");

        service.saveUser(userDTO, "EMPLOYEE");

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
        this.authService.authenticateCredential(model.email(), model.password());
        var user = this.service.findLoginInfo(model.email());
        var token = this.authService.generateToken(userMapper.userProjectionToUser().map(user));
        return ResponseEntity.ok(Map.of(
                "user", user,
                "token", token
        ));
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *<<<
     * @param oldToken Token JWT del usuario
     * @Authroization No necesita
     */
    @PostMapping(value = "/update-session", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, Object>> active(@RequestHeader("Authorization") String oldToken) {
        var email = this.authService.getCredentialEmail(oldToken.substring(7));
        var user = this.service.findUserByEmail(email);
        var token = this.authService.generateToken(user);

        return ResponseEntity.ok(Map.of(
                "user", user,
                "token", token
        ));
    }
}
