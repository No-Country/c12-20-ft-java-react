package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserKeysDTO;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserMapperBeans {
    private final PasswordEncoder encoder;
    @Bean
    public UserMapper<UserSaveDTO> saveDtoToUser(){
        return (dto) -> User.builder()
                .name(dto.name())
                .lastname(dto.lastname())
                .email(dto.email())
                .createDate(LocalDate.now())
                .password(encoder.encode(dto.password()))
                .build();
    }
}
