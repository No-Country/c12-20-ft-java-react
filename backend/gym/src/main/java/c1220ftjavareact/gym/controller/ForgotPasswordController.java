package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.service.interfaces.UpdatePasswordService;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UpdatedPasswordController {
    private final UpdatePasswordService passwordService;
    private final UserService userService;

    /**
     * Endpoint para iniciar el evento de actualizar contraseña si la olvidar
     *
     * @param email Email del usuario registrado que olvido su contraseña
     * @Authroization No necesita
     */
    @PostMapping(value = "/passwords/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, Boolean>> createUpdatePasswordEvent(
            @PathVariable("email") @Email String email
    ) {
        var model = this.userService.findUserAndUpdatePassword(email);

        if(model.getUpdatePassword() == null)

        return ResponseEntity.ok(Map.of("valid", passwordService.createUpdatePasswordEvent(email)));
    }

    /**
     * Endpoint para finalizar el Evento de actualizar contraseña si la olvido
     * En este endpoint validaria si el usuario que quiera cambiar la contraseña tenga un codigo valido
     *
     * @param code Codigo generado en el primer endpoint
     * @param id   Id del usuario que creo el code
     */
    @PutMapping(value = "/passwords", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> finishUpdatePasswordEvent(
            @RequestParam("code") String code,
            @RequestParam("id") String id
    ) {
        var event = this.passwordService.verifyUpdatePasswordEvent(code, id);

        this.passwordService.finishUpdatePasswordEvent(event);

        return ResponseEntity.ok().build();
    }
}
