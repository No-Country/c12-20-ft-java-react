package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;
import c1220ftjavareact.gym.domain.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.domain.mapper.ForgotPasswordMapperBean;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.ForgotPasswordRepository;
import c1220ftjavareact.gym.service.interfaces.ForgotPasswordService;
import c1220ftjavareact.gym.service.interfaces.UserService;
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
            throw new UpdatePasswordException("Ocurrio un error inesparado al guardar", "Puedes revisar: "+email+" y "+forgottenModel, ex.toString());
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
                        "El codigo no fue encontrado", "El codigo envido esta mal o no esta registrado", code)
                );

        return this.passwordMapper.entityToModel().map(forgotPassword);
    }

    @Override
    public void assertKeysEquals(String idModel, String idSaved) {
        if (!idSaved.equals(idModel)) {
            throw new UpdatePasswordException(
                    "Los codigos comparados no coinciden",
                    "Revisa bien los datos enviados",
                    idModel + " != " + idSaved
            );
        }
    }

    @Override
    public void assertIsNotExpired(LocalDateTime dateTime) {
        if (dateTime.isBefore(TimeUtils.getLocalDateTime())) {
            throw new UpdatePasswordException(
                    "EL codigo ya ha caducado",
                    "Crea un nuevo codigo",
                    dateTime.toString()
            );
        }
    }

    @Override
    public void assertIsEnable(Boolean enable) {
        if (!enable) {
            throw new UpdatePasswordException(
                    "El codigo ya no es valido",
                    "El codigo se desactiva al usarlo o al caducar",
                    enable.toString()
            );
        }
    }

    @Override
    public void updateForgottenPassword(UserPasswordDTO model) {
        var entity = this.passwordRepository
                .findById(Long.parseLong(model.id()))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado", "Revisa que el ID sea correcto", model.id()));
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
                                "El email no se encuentra registrado",
                                "Revisar bien el email enviado, o buscar si el registro esta eliminado",
                                email
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
