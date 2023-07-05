package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.Role;
import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserKeysDTO;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.domain.mapper.UserMapper;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.service.interfaces.AuthService;
import c1220ftjavareact.gym.service.interfaces.MailService;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final AuthService authService;
    private final MailService mailService;

    @Override
    public void registerCustomer(UserSaveDTO model) {
        var user = mapper.map(model);

        if (repository.existsByEmail(user.getEmail())) {
            throw new UserSaveException("El email" + user.getEmail() + "ya se encuentra registrado");
        }

        user.changeRole(Role.CUSTOMER);
        repository.saveAndFlush(user);
    }

    @Override
    public void registerEmployee(UserSaveDTO model) {
        var user = mapper.map(model);

        if (repository.existsByEmail(user.getEmail())) {
            throw new UserSaveException("El email" + user.getEmail() + "ya se encuentra registrado");
        }

        user.changeRole(Role.EMPLOYEE);

        if (!mailService.send(user.getEmail(), "Se ha creado tu cuenta de empleado en gym?¿", "Se ha creado tu cuenta crack")) {
            throw new RuntimeException("Email es invalido");
        }

        repository.saveAndFlush(user);
    }

    @Override
    public void registerAdmin() {
        var admin = mapper.map(UserSaveDTO.builder()
                .name("owner")
                .lastname("owner")
                .email("owner@gmail.com")
                .password("owner123")
                .build()
        );
        if (!repository.existsByEmail(admin.getEmail())) {
            admin.changeRole(Role.ADMIN);
            repository.saveAndFlush(admin);
        }
    }

    @Override
    public void authenticate(UserAuthDTO model) {
        var isAuthenticated = authService.authenticateCredential(model.email(), model.password());
        if (!isAuthenticated) {
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

    @Override
    public UserKeysDTO updateUserKeys(String token) {
        var email = authService.getCredentialEmail(token.substring(7));

        var user = repository.findByEmail(email);

        token = authService.generateToken(user.get());

        return UserKeysDTO.builder()
                .id(user.get().getId().toString())
                .role(user.get().getAuthorities().stream().findFirst().get().getAuthority())
                .token(token)
                .build();
    }
}
