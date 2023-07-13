package c1220ftjavareact.gym.password.service;

import c1220ftjavareact.gym.password.dto.ForgotPasswordMapperBean;
import c1220ftjavareact.gym.password.exception.UpdatePasswordException;
import c1220ftjavareact.gym.password.model.ForgotPassword;
import c1220ftjavareact.gym.password.repository.ForgotPasswordRepository;
import c1220ftjavareact.gym.security.dto.UserPasswordDTO;
import c1220ftjavareact.gym.user.dto.UserMapperBeans;
import c1220ftjavareact.gym.common.ResourceNotFoundException;
import c1220ftjavareact.gym.user.model.User;
import c1220ftjavareact.gym.user.service.UserService;
import c1220ftjavareact.gym.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordServiceImp implements ForgotPasswordService {
    private final ForgotPasswordRepository passwordRepository;
    private final ForgotPasswordMapperBean passwordMapper;
    private final UserMapperBeans userMapper;
    private final UserService userService;

    @Override
    public ForgotPassword generateForgotPassword(String id, String email) {
        //Se crea la instancia de Forgot password
        return ForgotPassword.builder()
                .id(id)
                .email(email)
                .enable(true)
                .code(UUID.randomUUID().toString())
                .expirationDate(TimeUtils.gerFormatedLocalDateTime().plusHours(1L))
                .build();
    }

    @Transactional
    @Override
    public Map<String, String> saveForgotPassword(String email) {
        User user = User.builder().build();
        var forgottenModel = ForgotPassword.builder().build();
        try {
            user = this.userService.findUserByEmail(email);

            //Genera la instancia de Forgot Password
            forgottenModel = this.generateForgotPassword(user.getId(), email);
            this.passwordRepository.saveForgotPassword(user.getId(), forgottenModel.code(), forgottenModel.expirationDate());
        } catch (Exception ex) {
            throw new UpdatePasswordException(
                    "Error en peticion cambio de contraseña", "Ha ocurrido un error inesperado al guardar el usuario"
            );
        }

        return Map.of(
                "id", user.getId().toString(),
                "fullName", user.fullname(),
                "code", forgottenModel.code()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ForgotPassword findByCode(String code) {
        //Arroja una excepcion si no encuentra la Instacio con el codigo
        var forgotPassword = passwordRepository.findByCode(code)
                .orElseThrow(() -> new UpdatePasswordException(
                        "Error en peticion cambio de contraseña", "No se ha encontrado una peticion con ese codigo"
                        )
                );

        return this.passwordMapper.entityToModel().map(forgotPassword);
    }

    @Override
    public void assertKeysEquals(String idModel, String idSaved) {
        if (!idSaved.equals(idModel)) {
            throw new UpdatePasswordException(
                    "Error en peticion cambio de contraseña", "Esta clave no pertenece al usuario solicitado"
            );
        }
    }

    @Override
    public void assertIsNotExpired(LocalDateTime dateTime) {
        if (dateTime.isBefore(TimeUtils.getLocalDateTime())) {
            throw new UpdatePasswordException(
                    "Error en peticion cambio de contraseña", "La peticion de contraseña ha caducado, vuelve a crear la peticion de cambio"
            );
        }
    }

    @Override
    public void assertIsEnable(Boolean enable) {
        if (!enable) {
            throw new UpdatePasswordException(
                    "Error en peticion cambio de contraseña", "La peticion de contraseña no esta habilitada, vuelva a crear la peticion"
            );
        }
    }

    @Override
    public void updateForgottenPassword(UserPasswordDTO model) {
        var entity = this.passwordRepository
                .findById(Long.parseLong(model.id()))
                .orElseThrow(() -> new ResourceNotFoundException(
                            "Recurso no encontrado", "El usuario todabia no ha echo una peticion de cambio de contraseña"
                        )
                );
        //Comprueba que los datos sean validos antes de actualizar<
        this.assertKeysEquals(model.code(), entity.getCode());
        this.assertIsEnable(entity.isEnable());
        this.assertIsNotExpired(entity.getExpirationDate());

        entity.getUserEntity().setPassword(userMapper.password().map(model.password()));
        entity.disable();

        this.passwordRepository.saveAndFlush(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public ForgotPassword findByEmail(String email) {
        //Arroja una excepcion si no encuentra la Instancia de Forgot Password con ese email
        var entity = passwordRepository.findByUserEntityEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Recurso no encontrado", "El usuario todabia no ha echo una peticion de cambio de contraseña"
                        )
                );
        return passwordMapper.entityToModel().map(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existsByEmail(String email) {
        return passwordRepository.existsByUserEntityEmail(email);
    }

}
