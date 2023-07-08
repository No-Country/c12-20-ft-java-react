package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.dto.UserForgotPasswordDTO;
import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;
import c1220ftjavareact.gym.domain.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.domain.mapper.ForgotPasswordMapperBean;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.ForgotPasswordRepository;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import c1220ftjavareact.gym.service.email.MailService;
import c1220ftjavareact.gym.service.email.TemplateStrategy;
import c1220ftjavareact.gym.service.interfaces.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordServiceImp implements ForgotPasswordService {
    private final ForgotPasswordRepository passwordRepository;
    private final ForgotPasswordMapperBean passwordMapper;
    private final UserMapperBeans userMapper;
    private final MailService mailService;
    private final UserRepository userRepository;

    /**
     * Crea una nueva instancia de ForgotPassword
     *
     * @param id ID del usuario que crea
     * @param email Email del usuario que crea
     *
     * @return {@link ForgotPassword}
     */
    @Override
    public ForgotPassword generateForgotPassword(String id, String email){
        var time = LocalDateTime.now(Clock.system(ZoneId.systemDefault()));
        return ForgotPassword.builder()
                .id(id)
                .email(email)
                .enable(true)
                .code(UUID.randomUUID().toString())
                .expirationDate(LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour()+1, time.getMinute()))
                .build();
    }

    /**
     * Guarda en la base de datos un {@code ForgotPasswordEntity}
     *
     * @param email Email del usuario que solicita el codigo
     *
     * @return {@link UserForgotPasswordDTO}
     */
    @Transactional
    @Override
    public UserForgotPasswordDTO saveForgotPassword(String email) {
        UserEntity userEntity = UserEntity.builder().build();
        var forgottenModel =  ForgotPassword.builder().build();
        try {
            userEntity = this.userRepository.findByEmail(email).get();
            forgottenModel = this.generateForgotPassword(userEntity.getId().toString(), email);
            this.passwordRepository.saveForgotPassword(userEntity.getId().toString(), forgottenModel.code(),forgottenModel.expirationDate());
        } catch (Exception ex) {
            throw new UpdatePasswordException(
                    "Ocurrio un error inesparado al guardar",
                    "Puedes revisar: " + email + " y " + forgottenModel,
                    ex.toString());
        }
        return new UserForgotPasswordDTO(forgottenModel, userMapper.userEntityToUser().map(userEntity));
    }

    /**
     * Verifica si es anterior al actual
     *
     * @param dateTime Tiempo a comprobar
     */
    @Override
    public Boolean isExpired(LocalDateTime dateTime) {
        return dateTime.isBefore( LocalDateTime.now(Clock.system(ZoneId.systemDefault())) );
    }

    /**
     * Envia un email con un template para recuperar la contraseña del usuario
     *
     * @param userForgotPassword Modelo con los datos para enviar el correo
     * @param strategy Template que se utilizara en el correo
     *
     * @return Si se ha envido correctamente el correo
     */
    @Override
    public Boolean sendRecoveryMessage(UserForgotPasswordDTO userForgotPassword, TemplateStrategy strategy){
        this.mailService.setTemplateStrategy(strategy);
        return mailService.send(
                userForgotPassword.getUser().getEmail(),
                "CAMBIO DE CONTRASEÑA",
                this.mailService.executeTemplate(
                        userForgotPassword.getUser().fullname(),
                "http://localhost:3300/password?code="+userForgotPassword.getForgotPassword().code()+"&id="+userForgotPassword.getUser().getId()
                )
        );
    }

    /**
     * Busca si un codigo esta relacionado con algun ForgotPassword registrado
     *
     * @param code Codigo
     *
     * @return {@link UserForgotPasswordDTO} Datos del Usuario y ForgotPassword
     */
    @Transactional(readOnly = true)
    @Override
    public UserForgotPasswordDTO findByCode(String code){
        var forgotPassword = passwordRepository.findByCode(code)
                .orElseThrow(() -> new UpdatePasswordException(
                                "El codigo no fue encontrado",
                                "El codigo envido esta mal o no esta registrado",
                                code
                        )
                );
       var userEntity = forgotPassword.getUserEntity();
       var userModel = this.userMapper.userEntityToUser().map(userEntity);
       var forgotModel = this.passwordMapper.entityToModel().map(forgotPassword);

       return new UserForgotPasswordDTO(forgotModel, userModel);
    }

    /**
     * Compara que los ID pasados sean iguales
     *
     * @param idModel ID del modelo que entro en la API
     * @param idSaved ID del modelo guardado en la base de datos
     */
    @Override
    public void AssertKeysEquals(String idModel, String idSaved){
        if(!idSaved.equals(idModel)){
            throw new UpdatePasswordException(
                    "Los codigos comparados no coinciden",
                    "Revisa bien los datos enviados",
                    idModel+" != "+idSaved
            );
        }
    }

    /**
     * Debe de dar falso o arroja una excepcion
     * @param dateTime Fecha y hora de caducidad
     */
    @Override
    public void AssertExpiredIsFalse(LocalDateTime dateTime){
        if(this.isExpired(dateTime)){
            throw new UpdatePasswordException(
                    "EL codigo ya ha caducado",
                    "Crea un nuevo codigo",
                    dateTime.toString()
            );
        }
    }

    /**
     * Debe de de ser enable true o arroja una excepcion
     *
     * @param enable Estado del codigo
     */
    @Override
    public void AssertIsEnable(Boolean enable){
        if (!enable) {
            throw new UpdatePasswordException(
                    "El codigo ya no es valido",
                    "El codigo se desactiva al usarlo o al caducar",
                    enable.toString()
            );
        }
    }

    /**
     * Actualiza la contrasela del usuario que ha sido olvidada
     *
     * @param model Modelo con datos para actualizar contraseña
     *
     */
    @Override
    public void updateForgottenPassword(UserPasswordDTO model) {
        var entity = this.passwordRepository
                .findById(Long.parseLong(model.id()))
                .orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado", "Revisa que el ID sea correcto", model.id()));

        this.AssertKeysEquals(model.code(),entity.getCode());
        this.AssertIsEnable(entity.isEnable());
        this.AssertExpiredIsFalse(entity.getExpirationDate());

        entity.getUserEntity().setPassword(userMapper.password().map(model.password()));
        entity.disable();
        this.passwordRepository.saveAndFlush(entity);
    }

    /**
     * Busco el usuario y los datos de la recuperacion de contraseña
     *
     * @param email Email
     * @return {@link ForgotPassword}
     */
    @Transactional(readOnly = true)
    @Override
    public ForgotPassword findByEmail(String email){
        var entity = passwordRepository.findForgotPasswordEntityByUserEntityEmail(email)
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
    public Boolean existsByEmail(String email){
        return passwordRepository.existsForgotPasswordEntityByUserEntityEmail(email);
    }

}
