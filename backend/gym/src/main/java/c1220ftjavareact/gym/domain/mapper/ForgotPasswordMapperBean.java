package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.UpdatePassword;
import c1220ftjavareact.gym.repository.entity.ForgotPasswordEntity;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class UpdatePasswordMapperBeans {
    @Bean
    public UpdatePasswordMapper<UserEntity, ForgotPasswordEntity> userToUpdatePassword() {
        var time = LocalDateTime.now(Clock.system(ZoneId.systemDefault()));
        return (user) -> ForgotPasswordEntity.builder()
                .id(user.getId())
                .userEntity(user)
                .expirationDate(LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour() + 1, time.getMinute()))
                .build();
    }

    public UpdatePasswordMapper<ForgotPasswordEntity, UpdatePassword> entityToModel() {
        return entity -> UpdatePassword.builder()
                .id(entity.getUserEntity().getId().toString())
                .code(entity.getCode())
                .email(entity.getUserEntity().getEmail())
                .expirationDate(entity.getExpirationDate())
                .enable(entity.isEnable())
                .build();
    }

    public UpdatePasswordMapper<UpdatePassword, ForgotPasswordEntity> modelToEntity() {
        return model -> ForgotPasswordEntity.builder()
                .id(Long.parseLong(model.id()))
                .code(model.code())
                .enable(model.enable())
                .expirationDate(model.expirationDate())
                .build();
    }

}
