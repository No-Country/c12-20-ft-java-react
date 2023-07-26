package c1220ftjavareact.gym.security.controller;

import c1220ftjavareact.gym.active.dto.ActiveUserDTO;
import c1220ftjavareact.gym.active.service.IActiveUserService;
import c1220ftjavareact.gym.email.UserCreatedStrategy;
import c1220ftjavareact.gym.events.event.UserCreatedEvent;
import c1220ftjavareact.gym.security.dto.UserAuthDTO;
import c1220ftjavareact.gym.security.dto.UserGoogleTokenDTO;
import c1220ftjavareact.gym.security.jwt.JwtService;
import c1220ftjavareact.gym.security.service.AuthService;
import c1220ftjavareact.gym.security.service.GoogleOauth2Service;
import c1220ftjavareact.gym.user.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.user.dto.UserSaveDTO;
import c1220ftjavareact.gym.user.dto.mapper.UserMapperBeans;
import c1220ftjavareact.gym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SuppressWarnings(value = "all")
public class AuthController {
    private final UserService service;
    private final AuthService springAuth;
    private final JwtService jwtService;
    private final UserMapperBeans userMapper;
    private final GoogleOauth2Service googleOauth2Service;
    private final ApplicationEventPublisher publisher;
    private final IActiveUserService iActiveUserService;

    /**
     * Endpoint para realizar el registro deL ADMIN (solo se crea una vez, datos por default)
     *
     * @Authorization No necesita
     */
    @PostMapping(value = "/admins")
    public HttpEntity<Void> adminSignUp() {
        var entity = userMapper.adminUser().map("owner1234");

        service.saveUser(userMapper.userEntityToUserSave().map(entity), "ADMIN");

        return ResponseEntity.created(URI.create("/api/v1/users/admins")).build();
    }

    /**
     * Endpoint para realizar el registro de un cliente
     *
     * @param userDTO DTO con los datos que se guardaran del cliente
     * @Authorization No necesita
     */
    @PostMapping("/customers")
    public HttpEntity<Void> customerSignUp(@Valid @RequestBody UserSaveDTO userDTO) {
        this.service.assertEmailIsNotRegistered(userDTO.email());

        this.service.saveUser(userDTO, "CUSTOMER");

        return ResponseEntity.created(URI.create("/api/v1/users/customers")).build();
    }

    /**
     * Endpoint para realizar el registro de un empleado
     *
     * @param employeeDTO DTO con los datos que se guardaran del empleado
     * @Authorization Si necesita Token y que el rol del usuario sea ADMIN
     */
    @PostMapping("/employees")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<Long> employeeSignUp(@Valid @RequestBody EmployeeSaveDTO employeeDTO) {
        this.service.assertEmailIsNotRegistered(employeeDTO.email());

        var values = service.saveEmployee(employeeDTO);

        publisher.publishEvent(new UserCreatedEvent(
                this,
                employeeDTO.email(),
                employeeDTO.name(),
                employeeDTO.lastname(),
                values.get("pass"),
                new UserCreatedStrategy())
        );

        return ResponseEntity.created(URI.create("/api/v1/users/employees")).body(Long.parseLong(values.get("Id")));
    }

    /**
     * Endpoint para realizar el registro de un cliente por Google
     *
     * @param employeeDTO DTO con los datos que se guardaran del empleado
     * @Authorization Si necesita Token y que el rol del usuario sea ADMIN
     */
    @PostMapping("/customers/google")
    public HttpEntity<Void> googleRegister(@Valid @RequestBody UserGoogleTokenDTO model) {
        //Compruebo la validez del token
        this.googleOauth2Service.isValidToken(model.token());
        //Recupero el email
        var email = this.googleOauth2Service.extractEmail(model.token());
        //Verifico que no este registrado
        this.service.assertEmailIsNotRegistered(email);
        //Recupero los datos del usuario
        var googleUser = this.googleOauth2Service.extractUser(model.token());
        //Hago el mapeo del GoogleUse a User
        var user = this.userMapper.userGoogleToUser().map(googleUser);
        //Guardo al usuario
        this.service.saveGoogleUser(user);
        //Publlico el evento UserCreated
        publisher.publishEvent(new UserCreatedEvent(
                this,
                user.getEmail(),
                user.getName(),
                user.getLastname(),
                user.getPassword(),
                new UserCreatedStrategy())
        );
        return ResponseEntity.created(URI.create("/api/v1/users/customers/google")).build();
    }

    /**
     * Endpoint para realizar el Login del usuario
     *
     * @param model Modelo con las credenciales del usuario
     * @Authroization No necesita
     */
    @PostMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, Object>> authentication(@RequestBody @Valid UserAuthDTO model) {
        //Se autentica las credenciales del usuario
        this.springAuth.authenticate(model.email(), model.password());

        //Se recupera la informacion para pasar el front
        var user = this.service.findLoginInfo(model.email());

        //Se crea el token con la Info
        var token = this.jwtService.generateToken(userMapper.userProjectionToUserEntity().map(user));

        ActiveUserDTO active = new ActiveUserDTO();
        active.setRole(user.getRole());
        active.setPicture(user.getPicture());
        active.setEmail(user.getEmail());
        active.setId(Long.valueOf(user.getId()));
        active.setFullName(user.getFullName());
        active.setToken(token);

        iActiveUserService.sendActiveUser(active);

        //Se envia el token
        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", user));
    }

    /**
     * Endpoint para realizar el Login del usuario
     *
     * @param model Modelo con las credenciales del usuario
     * @Authroization No necesita
     */
    @PostMapping(value = "/authentication/google", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, Object>> authenticationGoogle(@RequestBody @Valid UserGoogleTokenDTO model) {
        //Compruebo la validez del token
        this.googleOauth2Service.isValidToken(model.token());
        //Recupero el email
        var email = this.googleOauth2Service.extractEmail(model.token());
        this.springAuth.authenticate(email, "google");
        var user = this.service.findLoginInfo(email);
        var token = this.jwtService.generateToken(userMapper.userProjectionToUserEntity().map(user));
        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", user));
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *
     * @param oldToken Token JWT del usuario
     * @Authroization No necesita
     */
    @PostMapping(value = "/update-session", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, Object>> active(@RequestHeader("Authorization") String oldToken) {
        //Se recupera el email en base al token
        var email = this.jwtService.extractSubject(oldToken.substring(7));
        //Se busca si existe un usuario con ese email
        var user = this.service.findLoginInfo(email);
        //Se actualiza el token
        var token = this.jwtService.generateToken(userMapper.userProjectionToUserEntity().map(user));

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", user));
    }
}
