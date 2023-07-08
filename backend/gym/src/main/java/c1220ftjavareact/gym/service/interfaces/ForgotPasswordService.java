package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;

import java.time.LocalDateTime;
import java.util.Map;

public interface ForgotPasswordService {

    Map<String, String> saveForgotPassword(String email);
    ForgotPassword generateForgotPassword(String id, String email);

    Boolean isExpired(LocalDateTime dateTime);

    ForgotPassword findByCode(String code);

    void AssertKeysEquals(String idModel, String idSaved);

    void AssertIsNotExpired(LocalDateTime dateTime);

    void AssertIsEnable(Boolean enable);

    void updateForgottenPassword(UserPasswordDTO model);

    ForgotPassword findByEmail(String email);

    Boolean existsByEmail(String email);
}
