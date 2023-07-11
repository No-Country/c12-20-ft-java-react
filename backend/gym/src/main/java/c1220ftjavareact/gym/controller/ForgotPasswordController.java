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
@RequestMapping("/api/v1/passwords")
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {
    private final ForgotPasswordService passwordService;
    private final ApplicationEventPublisher publisher;

    /**
     * Endpoint para iniciar el evento de actualizar contraseña si la olvidaste
     *
     * @param email Email del usuario que solicita el cambio de contraseña
     * @Authroization No necesita
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> createForgotPassword(
            @RequestParam("email") @Email String email
    ) {
        if (this.passwordService.existsByEmail(email)) {
            var forgotPassword = this.passwordService.findByEmail(email);
            this.passwordService.assertIsEnable(forgotPassword.enable());
            this.passwordService.assertIsNotExpired(forgotPassword.expirationDate());
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
     * Endpoint para validar el codigo
     * Al ser valido se daria accesso a la URL para poder cambiar la contraseña
     *
     * @param code Codigo generado en el primer endpoint
     * @param id   Id del usuario que creo el code
     * @Authorization No necesita
     */
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> validateCode(
            @RequestParam("code") String code,
            @PathVariable("id") String id
    ) {
        var forgotPassword = this.passwordService.findByCode(code);
        this.passwordService.assertKeysEquals(id, forgotPassword.id());
        this.passwordService.assertIsEnable(forgotPassword.enable());
        this.passwordService.assertIsNotExpired(forgotPassword.expirationDate());

        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para actualizar la contraseña del Usuario
     * En este punto ya se enviaria la nueva contraseña
     *
     * @param dto Modelo con los datos para actualizar la contraseña
     * @Authroization No necesita
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> updateForgotenPassword(@RequestBody UserPasswordDTO dto) {
        if (!dto.password().equals(dto.repeatedPassword()))
            throw new UpdatePasswordException("Contraseñas distintas", "Ambas contraseñas que estas pasando debe de ser iguales", dto.password() + " != " + dto.repeatedPassword());

        this.passwordService.updateForgottenPassword(dto);

        return ResponseEntity.noContent().build();
    }
}
