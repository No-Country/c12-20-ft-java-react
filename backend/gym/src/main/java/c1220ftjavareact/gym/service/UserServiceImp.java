package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.Role;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.entity.User;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.domain.mapper.UserMapper;
import c1220ftjavareact.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImp extends AssertionConcern implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public void registerCustomer(UserSaveDTO model) {
        var user = mapper.map(model);

        if(repository.existsByEmail(user.getEmail()) ){
            throw new UserSaveException("El email"+user.getEmail()+"ya se encuentra registrado");
        }

        user.changeRoleS(Role.CUSTOMER);
        repository.saveAndFlush(user);
    }

    @Override
    public void registerEmployee(UserSaveDTO model) {
        var user = mapper.map(model);

        if(repository.existsByEmail(user.getEmail())){
            throw new UserSaveException("El email"+user.getEmail()+"ya se encuentra registrado");
        }

        user.changeRoleS(Role.EMPLOYEE);
        repository.saveAndFlush(user);
    }

    @Override
    public void registerAdmin(String password) {
        var admin = User.builder()
                .name("owner")
                .lastname("Owner")
                .email("owner@gmail.com")
                .password(password)
                .createDate(LocalDate.now())
                .role(Role.ADMIN)
                .build();

        if(!repository.existsByEmail(admin.getEmail())){
            repository.saveAndFlush(admin);
        }
    }
}
