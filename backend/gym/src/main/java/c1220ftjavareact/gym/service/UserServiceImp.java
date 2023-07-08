package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.*;
import c1220ftjavareact.gym.domain.exception.ResourceAlreadyExistsException;
import c1220ftjavareact.gym.domain.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.domain.exception.UpdatePasswordException;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.service.email.MailService;
import c1220ftjavareact.gym.service.email.TemplateStrategy;
import c1220ftjavareact.gym.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserMapperBeans userMapper;
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
     * @param id Id del usuario que se desea recuperar
     *
     * @return {@link User}
     */
    @Override
    public User findUserById(String id){
        var user = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                                "El ID no se encuentra registrado",
                                "El ID no esta en la base de datos o has puesto mal el ID",
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
    @Transactional
    @Override
    public void saveUser(UserSaveDTO model, String role) {
        try {
            repository.saveUser(model.name(), model.email(), model.lastname(), userMapper.password().map(model.password()), role);
        } catch (Exception ex) {
            throw new UserSaveException("Ocurrio un error al registar el empleado", "Revisa los datos el formato y que no haya datos nulos");
        }
    }

    /**
     * Guardo un Admin en la base de datos
     *
     * @param model Model de usuario para guardar
     * @param role  Role del usuario que se desea guardar
     *
     * @return {@link User}
     */
    @Transactional
    @Override
    public void saveAdmin(UserSaveDTO model, String role) {
        try {
            if(repository.countAdmins() < 1){
                repository.saveUser(model.name(), model.email(), model.lastname(), userMapper.password().map(model.password()), role);
            }
        } catch (Exception ex) {
            throw new UserSaveException("Ocurrio un error al registar el empleado", "Revisa los datos el formato y que no haya datos nulos");
        }
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
        user.setAvatar(StringUtils.hasText(dto.avatar()) ? dto.avatar() : user.getAvatar());
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
