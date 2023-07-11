package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.repository.entity.ForgotPasswordEntity;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import c1220ftjavareact.gym.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class ForgotPasswordMapperBean {

    @Bean
    public ForgotPasswordMapper<UserEntity, ForgotPasswordEntity> userToUpdatePassword() {
        return (user) -> ForgotPasswordEntity.builder()
                .id(user.getId())
                .userEntity(user)
                .expirationDate(TimeUtils.gerFormatedLocalDateTime())
                .build();
    }

    public ForgotPasswordMapper<ForgotPasswordEntity, ForgotPassword> entityToModel() {
        return entity -> ForgotPassword.builder()
                .id(entity.getUserEntity().getId().toString())
                .code(entity.getCode())
                .email(entity.getUserEntity().getEmail())
                .expirationDate(entity.getExpirationDate())
                .enable(entity.isEnable())
                .build();
    }

    public ForgotPasswordMapper<ForgotPassword, ForgotPasswordEntity> modelToEntity() {
        return model -> ForgotPasswordEntity.builder()
                .id(Long.parseLong(model.id()))
                .code(model.code())
                .enable(model.enable())
                .expirationDate(model.expirationDate())
                .build();
    }


}
