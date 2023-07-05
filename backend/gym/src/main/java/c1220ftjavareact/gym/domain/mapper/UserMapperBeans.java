package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserMapperBeans {
    private final PasswordEncoder encoder;

    @Bean
    public UserMapper<UserSaveDTO> saveDtoToUser() {
        return (dto) -> User.builder()
                .name(dto.name().toLowerCase(Locale.ROOT))
                .lastname(dto.lastname().toLowerCase(Locale.ROOT))
                .email(dto.email())
                .createDate(LocalDate.now())
                .password(encoder.encode(dto.password()))
                .build();
    }
}
