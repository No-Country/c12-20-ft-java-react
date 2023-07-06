package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.dto.UserLoginDTO;
import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.service.email.RecoveryPassStrategy;
import c1220ftjavareact.gym.service.interfaces.ForgotPasswordService;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {
    private final ForgotPasswordService passwordService;
    private final UserService service;

    /**
     * Endpoint para iniciar el evento de actualizar contraseña si la olvidar
     *
     * @param email Email del usuario registrado que olvido su contraseña
     * @Authroization No necesita
     */
    @PostMapping(value = "/passwords/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> createForgotPassword(
            @PathVariable("email") @Email String email
    ) {
        var user = this.service.findUserByEmail(email);
        ForgotPassword forgotPassword;
        if(this.passwordService.existsByEmail(email)){
            forgotPassword = this.passwordService.findByEmail(email);
            if(
                    forgotPassword.enable() &&
                   !this.passwordService.isExpired(forgotPassword.expirationDate())
            ){
                throw new UpdatePasswordException(
                        "Ya tines un token activo para cambiar tu contraseña",
                        "Busca el codigo en el email o elimina el registro",
                        forgotPassword.toString());
            }
        }
        forgotPassword = this.passwordService.saveForgotPassword(
                this.passwordService.createForgotPassword(user.getId(), user.getEmail())
        );

        if(forgotPassword == null)
            throw new UpdatePasswordException(
                    "Ocurrio un error inesperado al guardar",
                    "Revisa los datos del usuario o la base de datos",
                    user.toString());

        this.passwordService.sendRecoveryMessage(user, forgotPassword, new RecoveryPassStrategy());
        return ResponseEntity.ok().build();
    }


    /**
     * Endpoint para finalizar el Evento de actualizar contraseña si la olvido
     * En este endpoint validaria si el usuario que quiera cambiar la contraseña tenga un codigo valido
     *
     * @param code Codigo generado en el primer endpoint
     * @param id   Id del usuario que creo el code
     */
    @PostMapping(value = "/passwords", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> finishUpdatePasswordEvent(
            @RequestParam("code") String code,
            @RequestParam("id") String id
    ) {
        var userForgot = this.passwordService.findByCode(code);
        this.passwordService.validate(userForgot.getForgotPassword(), id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para actualizar el token del usuario si no ha expirado
     *
     * @param dto Token JWT del usuario
     * @return
     * @Authroization No necesita
     */
    @PutMapping(value = "/passwords", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> updatePassword(@RequestBody UserPasswordDTO dto) {
        if(!dto.password().equals(dto.repeatedPassword()))
            throw new RuntimeException("Contraseñas distintas");

        this.service.updateForgottenPassword(dto);

        return ResponseEntity.ok().build();
    }
}
