package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Configuration
public class UserMapperBeans {

    @Bean
    public UserMapper<UserSaveDTO> saveDtoToUser(){
        return (dto) -> User.builder()
                .name(dto.name())
                .lastname(dto.lastname())
                .email(dto.email())
                .createDate(LocalDate.now())
                .password(dto.password())
                .build();
    }

}
