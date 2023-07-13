package c1220ftjavareact.gym.user.dto.mapper;

import c1220ftjavareact.gym.security.dto.UserGoogleDTO;
import c1220ftjavareact.gym.user.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.user.dto.UserSaveDTO;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.user.enums.Role;
import c1220ftjavareact.gym.user.model.User;
import c1220ftjavareact.gym.user.projection.UserProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapperBeans {
    private final PasswordEncoder encoder;

    @Bean
    public UserMapper<String, String> password() {
        return encoder::encode;
    }

    @Bean
    public UserMapper<UserProjection, UserEntity> userProjectionToUserEntity() {
        return (dto) -> UserEntity.builder()
                .id(Long.parseLong(dto.getId()))
                .email(dto.getEmail())
                .name(dto.getFullName().split(" ")[0])
                .lastname(dto.getFullName().split(" ")[1])
                .role(dto.getRole())
                .picture(dto.getPicture())
                .build();
    }

    @Bean
    public UserMapper<String, UserEntity> adminUser() {
        return (pass) -> UserEntity.builder()
                .name("Owner-name")
                .lastname("Owner-lastname")
                .email("owner@gmail.com")
                .password(pass)
                .build();
    }


    @Bean
    public UserMapper<UserEntity, User> userEntityToUser() {
        return (entity) -> User.builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .createAt(entity.getCreateAt())
                .password(entity.getPassword())
                .role(entity.getRole().name())
                .picture(entity.getPicture())
                .build();
    }

    @Bean
    public UserMapper<UserEntity, UserSaveDTO> userEntityToUserSave() {
        return (entity) -> UserSaveDTO.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .lastname(entity.getLastname())
                .password(entity.getPassword())
                .build();
    }

    @Bean
    public UserMapper<User, UserEntity> userToUserEntity() {
        return (user) -> UserEntity.builder()
                .id(user.getId() != null ? Long.parseLong(user.getId()) : null)
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .createAt(user.getCreateAt())
                .password(user.getPassword())
                .role(Role.valueOf(user.getRole()))
                .picture(user.getPicture())
                .deleted(user.getDeleted())
                .build();
    }

    public UserMapper<EmployeeSaveDTO, UserSaveDTO> employeeSaveToUserSave() {
        return (dto) -> UserSaveDTO.builder()
                .name(dto.name())
                .lastname(dto.lastname())
                .email(dto.email())
                .password(UUID.randomUUID().toString().substring(0, 6))
                .build();
    }

    public UserMapper<UserGoogleDTO, User> userGoogleToUser() {
        return (dto) -> User.builder()
                .name(dto.name())
                .lastname(dto.lastName())
                .email(dto.email())
                .password(UUID.randomUUID().toString().substring(0, 6))
                .picture(dto.picture())
                .build();
    }
}
