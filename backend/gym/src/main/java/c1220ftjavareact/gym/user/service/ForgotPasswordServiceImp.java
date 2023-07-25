package c1220ftjavareact.gym.user.service;

import c1220ftjavareact.gym.security.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.user.dto.UserPasswordDTO;
import c1220ftjavareact.gym.user.dto.mapper.ForgotPasswordMapperBean;
import c1220ftjavareact.gym.user.dto.mapper.UserMapperBeans;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.user.exception.UpdatePasswordException;
import c1220ftjavareact.gym.user.model.ForgotPassword;
import c1220ftjavareact.gym.user.repository.ForgotPasswordRepository;
import c1220ftjavareact.gym.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public ForgotPassword generateForgotPassword(String id, String email) {
        //Se crea la instancia de Forgot password
        return ForgotPassword.builder()
                .id(id)
                .email(email)
                .enable(true)
                .code(UUID.randomUUID().toString())
                .expirationDate(TimeUtils.gerFormatedLocalDateTime().plusMinutes(5L))
                .build();
    }

    @Override
    public Long findIdByEmail(String email) {
        return this.passwordRepository.findByUserEntityEmail(email).get().getId();
    }

    @Transactional
    @Override
    public Map<String, String> saveForgot(String email) {
        var user = this.userRepository.findByEmail(email).get();

        var forgot = this.generateForgotPassword(user.getId().toString(), email);

        var entity = this.passwordMapper.modelToEntity().map(forgot);
        entity.setUserEntity(user);

        this.passwordRepository.save(entity);
        return Map.of(
                "id", user.getId().toString(),
                "fullName", user.fullname(),
                "code", forgot.code()
        );
    }

    @Transactional
    @Override
    public Map<String, String> saveForgotPassword(String email) {
        UserEntity user = UserEntity.builder().build();
        var forgottenModel = ForgotPassword.builder().build();
        try {
            user = this.userRepository.findByEmail(email).get();

            //Genera la instancia de Forgot Password
            forgottenModel = this.generateForgotPassword(user.getId().toString(), email);
            this.passwordRepository.saveForgotPassword(user.getId().toString(), forgottenModel.code(), 1, forgottenModel.expirationDate());
        } catch (Exception ex) {
            throw new UpdatePasswordException(
                    "Error in password change request.", "An unexpected error occurred while saving the user."
            );
        }

        return Map.of(
                "id", user.getId().toString(),
                "fullName", user.fullname(),
                "code", forgottenModel.code()
        );
    }

    @Transactional
    @Override
    public Map<String, String> createOtherPassword(ForgotPassword forgotPassword) {
        forgotPassword = this.generateForgotPassword(forgotPassword.id(), forgotPassword.email());
        var entity = this.passwordMapper.modelToEntity().map(forgotPassword);

        var userEntity = this.userRepository.findByEmail(forgotPassword.email()).get();

        entity.setUserEntity(userEntity);
        this.passwordRepository.save(entity);

        return Map.of(
                "id", forgotPassword.id().toString(),
                "fullName", userEntity.fullname(),
                "code", forgotPassword.code()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ForgotPassword findByCode(String code) {
        //Arroja una excepcion si no encuentra la Instacio con el codigo
        var forgotPassword = passwordRepository.findByCode(code)
                .orElseThrow(() -> new UpdatePasswordException(
                                "Error in password change request.", "No request found with that code."
                        )
                );

        return this.passwordMapper.entityToModel().map(forgotPassword);
    }

    @Override
    public void assertKeysEquals(String idModel, String idSaved) {
        if (!idSaved.equals(idModel)) {
            throw new UpdatePasswordException(
                    "Error in password change request.", "This key does not belong to the requested user."
            );
        }
    }

    @Override
    public void assertIsNotExpired(LocalDateTime dateTime, Long id) {
        if (dateTime.isBefore(TimeUtils.getLocalDateTime())) {
            this.passwordRepository.disable(id.toString());
            throw new UpdatePasswordException(
                    "Error in password change request.", "The password change request has already expired."
            );
        }
    }

    @Override
    public void assertIsExpired(LocalDateTime dateTime) {
        if (!dateTime.isBefore(TimeUtils.getLocalDateTime())) {
            throw new UpdatePasswordException(
                    "Error in password change request.", "You already have an active password change request."
            );
        }
    }

    @Override
    public void assertIsEnable(Boolean enable) {
        if (!enable) {
            throw new UpdatePasswordException(
                    "Error in password change request.", "The password change request has already expired."
            );
        }
    }

    @Override
    public void assertIsNotEnable(Boolean enable) {
        if (enable) {
            throw new UpdatePasswordException(
                    "Error in password change request.", "You already have an active password change request."
            );
        }
    }

    @Override
    public void updateForgottenPassword(UserPasswordDTO model) {
        var entity = this.passwordRepository
                .findById(Long.parseLong(model.id()))
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Resource not found.", "The user has not yet requested a password change"
                        )
                );
        //Comprueba que los datos sean validos antes de actualizar<
        this.assertKeysEquals(model.code(), entity.getCode());
        this.assertIsEnable(entity.isEnable());
        this.assertIsNotExpired(entity.getExpirationDate(), entity.getId());

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
                                "Resource not found", "First, you need to create a password change request."
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
