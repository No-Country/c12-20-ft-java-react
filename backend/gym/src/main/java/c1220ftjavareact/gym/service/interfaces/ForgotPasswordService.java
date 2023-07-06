package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.UserForgotPasswordDTO;
import c1220ftjavareact.gym.service.email.TemplateStrategy;

import java.time.LocalDateTime;

public interface ForgotPasswordService {

    ForgotPassword saveForgotPassword(ForgotPassword model);
    ForgotPassword createForgotPassword(String id, String email);

    void disableForgotPassword(UserForgotPasswordDTO model);

    Boolean isExpired(LocalDateTime dateTime);

    Boolean sendRecoveryMessage(User user, ForgotPassword forgotPassword, TemplateStrategy strategy);

    UserForgotPasswordDTO findByCode(String code);

    void validate(ForgotPassword model, String id);

    ForgotPassword findByEmail(String email);

    Boolean existsByEmail(String email);
}
