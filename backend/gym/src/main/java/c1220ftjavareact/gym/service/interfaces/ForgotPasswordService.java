package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.dto.UserForgotPasswordDTO;
import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;
import c1220ftjavareact.gym.service.email.TemplateStrategy;

import java.time.LocalDateTime;

public interface ForgotPasswordService {

    UserForgotPasswordDTO saveForgotPassword(String email);
    ForgotPassword generateForgotPassword(String id, String email);

    Boolean isExpired(LocalDateTime dateTime);

    Boolean sendRecoveryMessage(UserForgotPasswordDTO userForgotPassword, TemplateStrategy strategy);

    UserForgotPasswordDTO findByCode(String code);

    void AssertKeysEquals(String idModel, String idSaved);

    void AssertExpiredIsFalse(LocalDateTime dateTime);

    void AssertIsEnable(Boolean enable);

    void updateForgottenPassword(UserPasswordDTO model);

    ForgotPassword findByEmail(String email);

    Boolean existsByEmail(String email);
}
