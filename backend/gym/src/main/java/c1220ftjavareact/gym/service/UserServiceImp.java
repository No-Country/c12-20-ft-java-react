package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.Role;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.domain.mapper.UserMapper;
import c1220ftjavareact.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp extends AssertionConcern implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public void registerClient(UserSaveDTO model) {
        var user = mapper.map(model);

        if(repository.existsByEmail(user.getEmail()) ){
            throw new UserSaveException("El email"+user.getEmail()+"ya se encuentra registrado");
        }

        user.changeRoleS(Role.CLIENT);
        repository.saveAndFlush(user);
    }

    @Override
    public void registerUser(UserSaveDTO model) {
        var user = mapper.map(model);

        if(repository.existsByEmail(user.getEmail())){
            throw new UserSaveException("El email"+user.getEmail()+"ya se encuentra registrado");
        }

        user.changeRoleS(Role.USER);
        repository.saveAndFlush(user);
    }
}
