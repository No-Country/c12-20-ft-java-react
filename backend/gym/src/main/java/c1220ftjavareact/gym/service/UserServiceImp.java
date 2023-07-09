package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.*;
import c1220ftjavareact.gym.domain.exception.ResourceAlreadyExistsException;
import c1220ftjavareact.gym.domain.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.service.interfaces.UserService;
import c1220ftjavareact.gym.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserMapperBeans userMapper;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    @Override
    public void assertEmailIsNotRegistered(String email) {
        //Si el email esta registrado arroja la excepcion "ResourceAlreadyExistsException"
        if (repository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException(
                    "El email ya se encuentra registrado",
                    "El email " + email + " se encuentra en uso borra el registro o prueba otro",
                    email
            );
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(String id){
        var user = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                                "El ID no se encuentra registrado",
                                "El ID no esta en la base de datos o has puesto mal el ID",
                                id
                        )
                );
        //Mapeo el User de Jpa a un User normal
        return userMapper.userEntityToUser().map(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProjection findLoginInfo(String email){
        //Devuelve una Proyeccion solo con los datos necesarios
        return this.repository.findUserForLogin(email);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByEmail(String email) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "El email no se encuentra registrado",
                                "Revisar bien el email enviado, o buscar si el registro esta eliminado",
                                 email
                        )
                );
        //Mapeo el User de Jpa a un User normal
        return userMapper.userEntityToUser().map(user);
    }

    @Transactional
    @Override
    public void saveUser(UserSaveDTO model, String role) {
        try {
            //Si el Usuario es ADMIN y ya hay una instancia guardada arroja un error
            if(role.equals("ADMIN") && repository.countAdmins() == 1){
                throw new RuntimeException("Ya hay un admin registrado al sistema");
            }
            repository.saveUser(model.name(), model.email(), model.lastname(), userMapper.password().map(model.password()), role);
        } catch (Exception ex) {
            throw new UserSaveException("Ocurrio un error al registar el usuario", model.toString());
        }
    }

    @Override
    public void saveGoogleUser(User model) {
        try {
            model.setRole("CUSTOMER");
            model.setDeleted(false);
            model.setCreateAt(TimeUtils.getLocalDate());
            var entity = userMapper.userToUserEntity().map(model);
            entity.setPassword(userMapper.password().map(model.getPassword()));
            this.repository.save(entity);
        } catch (Exception ex) {
            throw new UserSaveException(ex.getMessage(), ex.toString());
        }
    }

    @Transactional
    @Override
    public void changeDeletedStateUser(String id, String role, Boolean state) {
        //Cuanta la cantidad de usuarios que existe con ese ID y Rol
        if(this.repository.countUsersBy(id, role) < 1){
            //Arroja la excepcion si no encuentra ni un registro
            throw new ResourceNotFoundException("El usuario no se encuentra registra", "Revisa que el ID pertenezca a un usuario con rol de empleado/EMPLOYEE", "ID: "+id);
        }
        //Cambia el estado del usuario
        this.repository.changeStateUser(id, role, state.equals(true) ? "1" : "0");
    }

    @Override
    public User updateUser(UserUpdateDTO dto, String id){
        //Busca el Usuario
        var user = this.repository.findById(Long.parseLong(id))
                .orElseThrow(()->new ResourceNotFoundException(
                                "Usuario no encontrado",
                                "Revisa que el ID sea correcto",
                                id
                        )
                );
        //Actualizo las propiedades solicitadas
        user.update(dto, encoder);

        //Guardo la Entidad y mapeo a un User normal
        return this.userMapper.userEntityToUser().map( this.repository.saveAndFlush(user) );
    }
}
