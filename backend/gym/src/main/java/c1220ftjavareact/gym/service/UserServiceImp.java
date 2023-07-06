package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.*;
import c1220ftjavareact.gym.domain.exception.ResourceAlreadyExistsException;
import c1220ftjavareact.gym.domain.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.domain.mapper.ForgotPasswordMapperBean;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.repository.entity.Role;
import c1220ftjavareact.gym.service.email.MailService;
import c1220ftjavareact.gym.service.email.TemplateStrategy;
import c1220ftjavareact.gym.service.interfaces.AuthService;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserMapperBeans userMapper;
    private final AuthService authService;
    private final MailService mailService;
    private final ForgotPasswordMapperBean forgotPassMapper;

    /**
     * Arroja un error si el email esta registrado
     *
     * @param email Correo a verificar
     */
    @Override
    public void assertEmailIsNotRegistered(String email) {
        if (repository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException(
                    "El email ya se encuentra registrado",
                    "El email " + email + " se encuentra en uso borra el registro o prueba otro",
                    email
            );
        }
    }

    /**
     * Busca un usuario por el correo
     *
     * @param email Correo del usuario que desea buscar
     * @return {@link User}
     */
    @Override
    public User findUserByEmail(String email) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "El email no se encuentra registrado",
                                "Revisar bien el email enviado, o buscar si el registro esta eliminado",
                                email
                        )
                );

        return userMapper.userEntityToUser().map(user);
    }

    /**
     * Guardo un usuario en la base de datos
     *
     * @param model Model de usuario para guardar
     * @param role  Role del usuario que se desea guardar
     *
     * @return {@link User}
     */
    public User saveUser(UserSaveDTO model, String role) {
        var user = userMapper.saveDtoToUser().map(model);
        user.setRole(Role.valueOf(role));
        try {
            var entity = repository.saveAndFlush(user);
            entity.setPassword(model.password());
            return userMapper.userEntityToUser().map(entity);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Guardo un usuario en la base de datos
     *
     * @param model Modelo con datos para actualizar contraseña
     *
     * @return {@link User}
     */
    @Override
    public void updateForgottenPassword(UserPasswordDTO model) {
        var user = this.repository.findById(Long.parseLong(model.id()))
                .orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado", "Revisa que el ID sea correcto", model.id()));
        if(!user.getUpdatePassword().getCode().equals(model.code())){
            throw new RuntimeException("El codigo no es igual al proporcionado");
        }
        if(
                !user.getUpdatePassword().isEnable() ||
                user.getUpdatePassword().isExpired(user.getUpdatePassword().getExpirationDate())
        ){
            throw new RuntimeException("El codigo ya ha caducado");
        }
        user.setPassword(userMapper.password().map(model.password()));
        user.getUpdatePassword().disable();
        this.repository.saveAndFlush(user);
    }

    /**
     * Envia un correo para avisar de la creacion de una cuenta
     *
     * @param model    Datos de usuario para enviar la cuenta
     * @param strategy Implementacion del template que se usara
     * @return Verdadero si pudo enviar, False si no
     */
    public Boolean sendCreateMessage(UserSaveDTO model, TemplateStrategy strategy) {
        var user = User.builder().name(model.name()).lastname(model.lastname()).build();
        mailService.setTemplateStrategy(strategy);
        return mailService.send(
                model.email(),
                "Se ha creado su cuenta en PrimeFit",
                mailService.executeTemplate(user.fullname(), model.email(), model.password()));
    }

    /**
     * Registra un administrador con datos por defecto
     */
    @Override
    public void registerAdmin() {
        var admin = userMapper.adminUser().map("owner@gmail.com");

        if (!repository.existsByEmail(admin.getEmail())) {
            admin.setRole(Role.ADMIN);
            repository.saveAndFlush(admin);
        }
    }

    /**
     * Verifica que las credenciales del usuario sean autenticas
     *
     * @param model Credenciales del usuario
     */
    @Override
    public void authenticate(UserAuthDTO model) {
        authService.authenticateCredential(model.email(), model.password());
    }

    /**
     * Recupera el Email del token
     *
     * @param token Token JWT
     * @return Email
     */
    @Override
    public String getEmailWithToken(String token) {
        return this.authService.getCredentialEmail(token);
    }

    /**
     * Crea un token JWT apartir de un {@link User}
     *
     * @param user Usuario que crea el token
     * @return Token JWT
     */
    @Override
    public String createToken(User user) {
        return this.authService.generateToken(user);
    }

    /**
     * Crea el model de datos para el Login
     *
     * @param token Token JWT
     * @return Nuevo Token JWT
     */
    @Override
    public UserLoginDTO getUserLogin(String token, User user) {
        var dto = userMapper.userToLoginUserDto().map(user);
        dto.token(token);
        return dto;
    }

}
