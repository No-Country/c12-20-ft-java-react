package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.Role;
import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserKeysDTO;
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
    private final AuthService authService;

    @Override
    public void registerCustomer(UserSaveDTO model) {
        var user = mapper.map(model);

        if(repository.existsByEmail(user.getEmail()) ){
            throw new UserSaveException("El email"+user.getEmail()+"ya se encuentra registrado");
        }

        user.changeRole(Role.CUSTOMER);
        repository.saveAndFlush(user);
    }

    @Override
    public void registerEmployee(UserSaveDTO model) {
        var user = mapper.map(model);

        if(repository.existsByEmail(user.getEmail())){
            throw new UserSaveException("El email"+user.getEmail()+"ya se encuentra registrado");
        }

        user.changeRole(Role.EMPLOYEE);
        repository.saveAndFlush(user);
    }

    @Override
    public void registerAdmin() {
        var admin = mapper.map(UserSaveDTO.builder()
                .name("owner")
                .lastname("Owner")
                .email("owner@gmail.com")
                .password("owner123")
                .build());
        if(!repository.existsByEmail(admin.getEmail())){
            admin.changeRole(Role.ADMIN);
            repository.saveAndFlush(admin);
        }
    }

    @Override
    public void authenticate(UserAuthDTO model) {
        var isAuthenticated = authService.authenticateCredential(model.email(), model.password());
        if( !isAuthenticated ){
            throw new RuntimeException("Las credenciales no son autenticas");
        }
    }

    @Override
    public UserKeysDTO generateUserKeys(String email) {
        var user = repository.findByEmail(email);

        var token = authService.generateToken(user.get());
        return UserKeysDTO.builder()
                .id(user.get().getId().toString())
                .role(user.get().getAuthorities().stream().findFirst().get().getAuthority())
                .token(token)
                .build();
    }
}
