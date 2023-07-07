package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.UserForgotPasswordDTO;
import c1220ftjavareact.gym.domain.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.domain.mapper.ForgotPasswordMapperBean;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.ForgotPasswordRepository;
import c1220ftjavareact.gym.repository.UserRepository;
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
    public ForgotPassword createForgotPassword(String id, String email){
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
     * @param model Model con los datos del ForgotPassword
     * @param model Modelo con los datos del Usuario
     *
     * @return {@link ForgotPassword}
     */
    @Transactional
    @Override
    public ForgotPassword saveForgotPassword(ForgotPassword model){
        try{
            var userEntity = this.userRepository.findByEmail(model.email()).get();
            var entity = passwordMapper.modelToEntity().map(model);
            userEntity.setUpdatePassword(entity);
            userRepository.saveAndFlush(userEntity);
        } catch (Exception ex){
            return null;
        }
        return model;
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
     * @param user Modelo con los datos del usuario
     * @param forgotPassword Modelo con los datos del ForgotPassword
     * @param strategy Template que se utilizara en el correo
     *
     * @return Si se ha envido correctamente el correo
     */
    @Override
    public Boolean sendRecoveryMessage(User user, ForgotPassword forgotPassword, TemplateStrategy strategy){
        this.mailService.setTemplateStrategy(strategy);
        return mailService.send(
                user.getEmail(),
                "CAMBIO DE CONTRASEÑA",
                this.mailService.executeTemplate(
                        user.fullname(),
                        "http://localhost:3300/password?code="+forgotPassword.code()+"&id="+user.getId()
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
     * Valida si el usuario que quiere cambiar la contraseña es valido
     *
     * @param model Modelo con los datos del ForgotPassword
     * @param id ID del usuario que solicito la validacion
     */
    @Override
    public void validate(ForgotPassword model , String id) {
        if ( model.id().equals(Long.parseLong(id)) ) {
            throw new UpdatePasswordException(
                    "Codigo no pertene al usuario",
                    "El ID registrado " + model.id() + " no coincide con ID "+id,
                    model.toString());
        }

        if (!model.enable()) {
            throw new UpdatePasswordException(
                    "El codigo ya no es valido",
                    "El codigo se desactiva al usarlo o al caducar",
                    model.toString()
            );
        }
        if(this.isExpired(model.expirationDate())){
            throw new UpdatePasswordException(
                    "EL codigo ya ha caducado",
                    "Crea un nuevo codigo",
                    model.toString());
        }
    }

    /**
     * Busco el usuario y los datos de la recuperacion de contraseña
     *
     * @param email Email
     * @return {@link UserForgotPasswordDTO}
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
