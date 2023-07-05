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
    public UpdatePasswordMapper<User> saveDtoToUser() {
        return (user) -> UpdatePassword.builder()
                .user(user)
                .enable(true)
                .build();
    }
}
