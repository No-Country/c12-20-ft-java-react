package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;
import c1220ftjavareact.gym.domain.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.domain.mapper.ForgotPasswordMapperBean;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.ForgotPasswordRepository;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import c1220ftjavareact.gym.service.interfaces.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordServiceImp implements ForgotPasswordService {
    private final ForgotPasswordRepository passwordRepository;
    private final ForgotPasswordMapperBean passwordMapper;
    private final UserMapperBeans userMapper;
    private final UserRepository userRepository;

    @Override
    public ForgotPassword generateForgotPassword(String id, String email){
        //Se crea la instancia de Forgot password
        var time = LocalDateTime.now(Clock.system(ZoneId.systemDefault()));
        return ForgotPassword.builder()
                .id(id)
                .email(email)
                .enable(true)
                .code(UUID.randomUUID().toString())
                .expirationDate(LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour()+1, time.getMinute()))
                .build();
    }

    @Transactional
    @Override
    public Map<String, String> saveForgotPassword(String email) {
        UserEntity userEntity = UserEntity.builder().build();
        var forgottenModel =  ForgotPassword.builder().build();
        try {
            //Busca el usuario por el email
            userEntity = this.userRepository.findByEmail(email).get();
            //Genera la instancia de Forgot Password
            forgottenModel = this.generateForgotPassword(userEntity.getId().toString(), email);
            //Guarda en la base de datos
            this.passwordRepository.saveForgotPassword(userEntity.getId().toString(), forgottenModel.code(),forgottenModel.expirationDate());
        } catch (Exception ex) {
            throw new UpdatePasswordException(
                    "Ocurrio un error inesparado al guardar",
                    "Puedes revisar: " + email + " y " + forgottenModel,
                    ex.toString());
        }
        //Envio los datos necesarios en un Map
        return Map.of(
                "id", userEntity.getId().toString(),
                "fullName", userEntity.fullname(),
                "code", forgottenModel.code()
        );
    }

    @Override
    public Boolean isExpired(LocalDateTime dateTime) {
        return dateTime.isBefore( LocalDateTime.now(Clock.system(ZoneId.systemDefault())) );
    }

    @Transactional(readOnly = true)
    @Override
    public ForgotPassword findByCode(String code){
        //Arroja una excepcion si no encuentra la Instacio con el codigo
        var forgotPassword = passwordRepository.findByCode(code)
                .orElseThrow(() -> new UpdatePasswordException(
                                "El codigo no fue encontrado",
                                "El codigo envido esta mal o no esta registrado",
                                code
                        )
                );
        //Mapeo la entidad Jpa a un Forgot password normal
       return this.passwordMapper.entityToModel().map(forgotPassword);
    }

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

    @Override
    public void AssertIsNotExpired(LocalDateTime dateTime){
        if(dateTime.isBefore( LocalDateTime.now(Clock.system(ZoneId.systemDefault())) )){
            throw new UpdatePasswordException(
                    "EL codigo ya ha caducado",
                    "Crea un nuevo codigo",
                    dateTime.toString()
            );
        }
    }

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

    @Override
    public void updateForgottenPassword(UserPasswordDTO model) {
        var entity = this.passwordRepository
                .findById(Long.parseLong(model.id()))
                .orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado", "Revisa que el ID sea correcto", model.id()));
        //Comprueba que los datos sean validos antes de actualizar<
        this.AssertKeysEquals(model.code(),entity.getCode());
        this.AssertIsEnable(entity.isEnable());
        this.AssertIsNotExpired(entity.getExpirationDate());

        entity.getUserEntity().setPassword(userMapper.password().map(model.password()));
        entity.disable();

        this.passwordRepository.saveAndFlush(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public ForgotPassword findByEmail(String email){
        //Arroja una excepcion si no encuentra la Instancia de Forgot Password con ese email
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
