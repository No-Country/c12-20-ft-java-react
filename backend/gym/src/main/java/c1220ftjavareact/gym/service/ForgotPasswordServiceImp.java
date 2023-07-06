package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.UpdatePassword;
import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.domain.mapper.UpdatePasswordMapperBeans;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.UpdatePasswordRepository;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.service.email.MailService;
import c1220ftjavareact.gym.service.email.RecoveryPassStrategy;
import c1220ftjavareact.gym.service.interfaces.UpdatePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdatePasswordServiceImp implements UpdatePasswordService {
    private final UpdatePasswordRepository passwordRepository;
    private final UpdatePasswordMapperBeans passwordMapper;
    private final UserMapperBeans userMapper;
    private final MailService mailService;
    private final UserRepository userRepository;

    @Override
    public UpdatePassword createUpdatePassword(String id, String email){
        var time = LocalDateTime.now(Clock.system(ZoneId.systemDefault()));
        return UpdatePassword.builder()
                .id(id)
                .email(email)
                .enable(true)
                .code(UUID.randomUUID().toString())
                .expirationDate(LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour()+1, time.getMinute()))
                .build();
    }

    @Transactional
    @Override
    public void savePasswordEvent(UpdatePassword model, User user){
        var entity = passwordMapper.modelToEntity().map(model);
        var userEntity = userMapper.userToUserEntity().map(user);
        entity.setUserEntity(userEntity);
        passwordRepository.saveAndFlush(entity);
    }

    @Transactional
    @Override
    public Boolean createUpdatePasswordEvent(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El email no se encuentra registrado en el sistema"));


        var updatePassword = passwordMapper.userToUpdatePassword().map(user);
        updatePassword.enable();
        updatePassword.generateCode();
        user.setUpdatePassword(updatePassword);

        if (!updatePassword.isExpired()) {
            passwordRepository.flush();
            passwordRepository.save(updatePassword);
        } else {
            throw new UpdatePasswordException("Ya tienes un token de actualizacion activo", "revisa la fecha del codigo");
        }
        this.mailService.setTemplateStrategy(new RecoveryPassStrategy());
        return mailService.send(
                user.getEmail(),
                "CAMBIO DE CONTRASEÑA",
                this.mailService.executeTemplate(
                        user.fullname(),
                        "http://localhost:3300/password?code=" + updatePassword.getCode() + "&id=" + user.getId()
                )
        );
    }

    @Transactional
    @Override
    public UpdatePassword verifyUpdatePasswordEvent(String code, String id) {
        var updatePassword = passwordRepository.findByCode(code)
                .orElseThrow(() -> new UpdatePasswordException("El codigo no fue encontrado", "El codigo envido esta mal o no esta registrado"));
        updatePassword.getUserEntity();
        if (!updatePassword.getUserEntity().getId().equals(Long.parseLong(id))) {
            throw new UpdatePasswordException("Codigo no pertene al usuario", "El usuario con ID: " + id + " no tiene un codigo registrado igual al que haz pasado");
        }
        if (!updatePassword.isEnable()) {
            throw new UpdatePasswordException("El codigo ya no es valido", "El codigo se desactiva al usarlo o al caducar");
        }
        if (updatePassword.isExpired()) {
            updatePassword.disable();
            passwordRepository.save(updatePassword);
            throw new UpdatePasswordException("El codigo ya ha caducado", "El tiempo de vida de este codigo ha terminado");
        }
        updatePassword.disable();
        return passwordMapper.entityToModel().map(updatePassword);
    }

    @Transactional
    @Override
    public void finishUpdatePasswordEvent(UpdatePassword updatePassword) {
        passwordRepository.saveAndFlush(passwordMapper.modelToEntity().map(updatePassword));
    }


}
