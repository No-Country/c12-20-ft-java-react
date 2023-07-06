package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.UserLoginDTO;
import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;
import c1220ftjavareact.gym.service.interfaces.ForgotPasswordService;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;
    private final ForgotPasswordService passwordService;

    /**
     * Endpoint para realizar el Login del usuario
     *
     * @param model Modelo con las credenciales del usuario
     * @return
     * @Authroization No necesita
     */
    @PostMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserLoginDTO> authentication(@RequestBody @Valid UserAuthDTO model) {
        service.authenticate(model);
        var user = this.service.findUserByEmail(model.email());
        var token = this.service.createToken(user);
        var response = this.service.getUserLogin(token, user);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *
     * @param oldToken Token JWT del usuario
     * @return
     * @Authroization No necesita
     */
    @PostMapping(value = "/update-session", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserLoginDTO> active(@RequestHeader("Authorization") String oldToken) {
        var email = this.service.getEmailWithToken(oldToken.substring(7));

        var user = this.service.findUserByEmail(email);

        var token = this.service.createToken(user);

        var response = this.service.getUserLogin(token, user);

        return ResponseEntity.ok(response);
    }

}
