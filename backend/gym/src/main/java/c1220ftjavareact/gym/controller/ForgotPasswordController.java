package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.events.event.RecoveryPasswordEvent;
import c1220ftjavareact.gym.service.email.RecoveryPassStrategy;
import c1220ftjavareact.gym.service.interfaces.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {
    private final ForgotPasswordService passwordService;
    private final ApplicationEventPublisher publisher;

    /**
     * Endpoint para iniciar el evento de actualizar contraseña si la olvidar
     *
     * @param email Email del usuario registrado que olvido su contraseña
     * @Authroization No necesita
     */
    @PostMapping(value = "/passwords", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> createForgotPassword(
            @RequestParam("email") @Email String email
    ) {
        if(this.passwordService.existsByEmail(email)){
            var forgotPassword = this.passwordService.findByEmail(email);
            this.passwordService.AssertIsEnable(forgotPassword.enable());
            this.passwordService.AssertIsNotExpired(forgotPassword.expirationDate());
        }

        var values = this.passwordService.saveForgotPassword(email);
        this.publisher.publishEvent(new RecoveryPasswordEvent(
                this,
                values.get("id"),
                email,
                values.get("fullName"),
                values.get("code"),
                new RecoveryPassStrategy()
        ));
        return ResponseEntity.noContent().build();
    }


    /**
     * Endpoint para finalizar el Evento de actualizar contraseña si la olvido
     * En este endpoint validaria si el usuario que quiera cambiar la contraseña tenga un codigo valido
     *
     * @param code Codigo generado en el primer endpoint
     * @param id   Id del usuario que creo el code
     */
    @PostMapping(value = "/passwords/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> validateCode(
            @RequestParam("code") String code,
            @PathVariable("id") String id
    ) {
        var forgotPassword = this.passwordService.findByCode(code);
        this.passwordService.AssertKeysEquals(id, forgotPassword.id());
        this.passwordService.AssertIsEnable(forgotPassword.enable());
        this.passwordService.AssertIsNotExpired(forgotPassword.expirationDate());

        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *
     * @param dto Token JWT del usuario
     * @return
     * @Authroization No necesita
     */
    @PutMapping(value = "/passwords", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> updateForgotenPassword(@RequestBody UserPasswordDTO dto) {
        if(!dto.password().equals(dto.repeatedPassword()))
            throw new UpdatePasswordException("Contraseñas distintas", "Ambas contraseñas que estas pasando debe de ser iguales", dto.password()+" != "+dto.repeatedPassword());

        this.passwordService.updateForgottenPassword(dto);

        return ResponseEntity.noContent().build();
    }
}
