package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.repository.entity.UpdatePassword;
import c1220ftjavareact.gym.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePasswordMapperBeans {

    @Bean
    public UpdatePasswordMapper<User> userToUpdatePassword() {
        return (user) -> UpdatePassword.builder()
                .id(user.getId())
                .user(user)
                .enable(true)
                .build();
    }
}
