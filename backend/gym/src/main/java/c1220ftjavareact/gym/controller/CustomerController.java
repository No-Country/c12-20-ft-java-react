package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final UserService service;

    /**
     * Endpoint para realizar el registro de un cliente
     *
     * @param userDTO DTO con los datos que se guardaran del cliente
     * @Authorization No necesita
     */
    @PostMapping
    public HttpEntity<Void> registerCustomer(@Valid @RequestBody UserSaveDTO userDTO) {
        this.service.assertEmailIsNotRegistered(userDTO.email());

        var user = this.service.saveUser(userDTO, "CUSTOMER");

        if (user == null)
            throw new UserSaveException("Ocurrio un error inesperado en el registro", "Revisa los datos el formato y que no haya datos nulos");

        return ResponseEntity.created(URI.create("/api/v1/customers")).build();
    }
}
