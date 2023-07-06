package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.service.email.CustomerCreate;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;
    private final UserMapperBeans mapper;

    /**
     * Endpoint para realizar el registro de un empleado
     *
     * @param employeeDTO DTO con los datos que se guardaran del empleado
     * @Authorization Si necesita Token y que el rol del usuario sea ADMIN
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<Void> registerEmployee(@Valid @RequestBody EmployeeSaveDTO employeeDTO) {
        this.service.assertEmailIsNotRegistered(employeeDTO.email());
        var userDTO = this.mapper.employeeSaveToUserSave().map(employeeDTO);
        if (!this.service.sendCreateMessage(userDTO, new CustomerCreate()))
            throw new UserSaveException("Error al enviar el email", "Espera y vuelve a intentarlo o revisa el formato del email");

        var user = service.saveUser(userDTO, "EMPLOYEE");
        if (user == null)
            throw new UserSaveException("Ocurrio un error al registar el empleado", "Revisa los datos el formato y que no haya datos nulos");

        return ResponseEntity.created(URI.create("/api/v1/admins")).build();
    }

    /**
     * Endpoint para realizar el registro deL ADMIN (solo se crea una vez, datos por default)
     *
     * @Authorization No necesita
     */
    @PostMapping(value = "/create")
    public HttpEntity<Void> registerAdmin() {
        service.registerAdmin();
        return ResponseEntity.created(URI.create("/api/v1/admins")).build();
    }
}
