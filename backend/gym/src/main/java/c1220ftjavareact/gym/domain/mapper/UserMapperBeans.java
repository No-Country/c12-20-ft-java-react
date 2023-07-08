package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.domain.dto.UserProjection;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.dto.UserUpdateDTO;
import c1220ftjavareact.gym.repository.entity.Role;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
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
                .role(dto.getRole())
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
                .avatar(entity.getAvatar())
                .build();
    }

    @Bean
    public UserMapper<UserEntity, UserSaveDTO> userEntityToUserSave() {
        return (entity) -> UserSaveDTO.builder()
                .name(entity.getName() )
                .email(entity.getEmail() )
                .lastname(entity.getLastname())
                .password(entity.getPassword())
                .build();
    }

    @Bean
    public UserMapper<User, UserEntity> userToUserEntity() {
        return (user) -> UserEntity.builder()
                .id(Long.parseLong(user.getId()))
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .createAt(user.getCreateAt())
                .password(user.getPassword())
                .role(Role.valueOf(user.getRole()))
                .avatar(user.getAvatar())
                .build();
    }

    @Bean
    public UserMapper<User, UserDetails> userToUserDetails() {
        return (user) -> UserEntity.builder()
                .id(Long.parseLong(user.getId()))
                .email(user.getEmail())
                .role(Role.valueOf(user.getRole()))
                .build();
    }

    public UserMapper<EmployeeSaveDTO, UserSaveDTO> employeeSaveToUserSave(){
        return (dto)->UserSaveDTO.builder()
                .name(dto.name())
                .lastname(dto.lastname())
                .email(dto.email())
                .password(UUID.randomUUID().toString().substring(0, 6))
                .build();
    }
}
