package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.service.interfaces.UpdatePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UpdatedPasswordController {
    private final UpdatePasswordService passwordService;

    @PostMapping(value = "/passwords", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Map<String, Boolean>> initializeChangePassword(
            @RequestBody @Email @NotEmpty String email
    ) {
        if (!passwordService.verifyEmail(email))
            return ResponseEntity.badRequest().body(Map.of("valid", true));

        return ResponseEntity.ok(Map.of("valid", passwordService.updatePasswordEvent(email)));
    }


    @PutMapping(value = "/passwords", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> validateEvent(
            @RequestParam("code") String code,
            @RequestParam("id") String id
    ) {
        passwordService.checkEmail(code, id);

        return ResponseEntity.ok().build();
    }
}
