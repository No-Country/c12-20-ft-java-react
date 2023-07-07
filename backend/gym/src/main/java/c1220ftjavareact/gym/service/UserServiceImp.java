package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.*;
import c1220ftjavareact.gym.domain.exception.ResourceAlreadyExistsException;
import c1220ftjavareact.gym.domain.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.repository.entity.Role;
import c1220ftjavareact.gym.service.email.MailService;
import c1220ftjavareact.gym.service.email.TemplateStrategy;
import c1220ftjavareact.gym.security.service.AuthService;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserMapperBeans userMapper;

    private final AuthService authService;

    private final MailService mailService;
    private final PasswordEncoder encoder;


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
     * Busca un Usuario por ID
     *
     * @param id
     *
     * @return {@link User}
     */
    @Override
    public User findUserById(String id){
        var user = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                                "El ID no se encuentra registrado",
                                "El ID no esta en la base de datos o haz puesto mal el numero",
                                id
                        )
                );

        return userMapper.userEntityToUser().map(user);
    }

    /**
     * Recupera una proyeccion del Usuario con los datos para el login
     *
     * @param email
     *
     * @return {@link UserProjection}
     */
    @Override
    public UserProjection findLoginInfo(String email){
        return this.repository.findUserForLogin(email);
    }

    /**
     * Busca un usuario por el correo
     *
     * @param email Correo del usuario que desea buscar
     *
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
     * Cambia la cantraseña olvidada del usuario
     *
     * @param model Modelo con datos para actualizar contraseña
     *
     */
    @Override
    public void updateForgottenPassword(UserPasswordDTO model) {
        var user = this.repository.findById(Long.parseLong(model.id()))
                .orElseThrow(()->new ResourceNotFoundException(
                        "Usuario no encontrado",
                        "Revisa que el ID sea correcto",
                        model.id()
                        )
                );
        if(!user.getUpdatePassword().getCode().equals(model.code())){
            throw new UpdatePasswordException(
                    "El codigo del usuario no es igual al proporcionado",
                    "Revisa que hayas envido el ID bien y escrito bien el codigo",
                    user.getUpdatePassword().getCode()+" != "+model.code()
            );
        }
        if(
                !user.getUpdatePassword().isEnable() ||
                user.getUpdatePassword().isExpired(user.getUpdatePassword().getExpirationDate())
        ){
            throw new UpdatePasswordException(
                    "El codigo ya ha caducado",
                    "Crea un nueco codigo este ya es invalido",
                    "Estado: "+user.getUpdatePassword().isEnable()+", caducidad: "+user.getUpdatePassword().getExpirationDate().toString()
            );
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
     *
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

    @Override
    public void userLogicalDeleteById(String id, String role) {

        this.repository.deleteUsersBy(id, role);
    }

    @Override
    public User updateUser(UserUpdateDTO dto, String id){
        var user = this.repository.findById(Long.parseLong(id))
                .orElseThrow(()->new ResourceNotFoundException(
                                "Usuario no encontrado",
                                "Revisa que el ID sea correcto",
                                id
                        )
                );

        user.setName(StringUtils.hasText(dto.name()) ? dto.name() : user.getName());
        user.setLastname(StringUtils.hasText(dto.lastName()) ? dto.lastName() : user.getLastname());
        user.setEmail(StringUtils.hasText(dto.email()) ? dto.email() : user.getEmail());
        if(     StringUtils.hasText(dto.updatedPassword())
        ){
            if(!encoder.matches(dto.oldPassword(), user.getPassword())){
                throw new UserSaveException("La contraseña antigua es incorrecta", "Poner la contraseña registrada, o no poner los datos de cambio de contraseña");
            }
            user.setPassword(userMapper.password().map(dto.updatedPassword()));
        }
        var entity =this.repository.saveAndFlush(user);
        return this.userMapper.userEntityToUser().map(entity);
    }
}
