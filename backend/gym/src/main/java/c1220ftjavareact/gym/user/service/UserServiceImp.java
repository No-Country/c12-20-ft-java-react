package c1220ftjavareact.gym.user.service;

import c1220ftjavareact.gym.user.dto.mapper.UserMapperBeans;
import c1220ftjavareact.gym.user.dto.UserSaveDTO;
import c1220ftjavareact.gym.user.dto.UserUpdateDTO;
import c1220ftjavareact.gym.common.ResourceAlreadyExistsException;
import c1220ftjavareact.gym.common.ResourceNotFoundException;
import c1220ftjavareact.gym.user.exception.UserSaveException;
import c1220ftjavareact.gym.user.model.User;
import c1220ftjavareact.gym.user.projection.UserProjection;
import c1220ftjavareact.gym.user.repository.UserRepository;
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
                    "Recurso ya esta registrado", "El email ya se encuentra en uso"
            );
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(String id) {
        var user = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Recurso no encontrado", "El usuario no fue encontrado para actualizarlo"
                        )
                );
        //Mapeo el User de Jpa a un User normal
        return userMapper.userEntityToUser().map(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProjection findLoginInfo(String email) {
        //Devuelve una Proyeccion solo con los datos necesarios
        return this.repository.findUserForLogin(email);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByEmail(String email) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Recurso no encontrado", "El usuario no fue encontrado para actualizarlo"
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
            if (role.equals("ADMIN") && repository.countAdmins() == 1) {
                throw new UserSaveException("Error al guardar usuario", "Ya hay un admin registrado, no es posible crear dos admins");
            }
            repository.saveUser(model.name(), model.email(), model.lastname(), userMapper.password().map(model.password()), role);
        } catch (Exception ex) {
            throw new UserSaveException("Error al guardar usuario", "Ocurrio un error inesperado en el guardado del usuario");
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
            throw new UserSaveException("Error al guardar usuario", "Ocurrio un error inesperado en el guardado del usuario");
        }
    }

    @Transactional
    @Override
    public void changeDeletedStateUser(String id, String role, Boolean state) {
        //Cuanta la cantidad de usuarios que existe con ese ID y Rol
        if (this.repository.countUsersBy(id, role) < 1) {
            throw new ResourceNotFoundException(
                    "Recurso no encontrado", "El usuario no es un empleado"
            );
        }
        //Cambia el estado del usuario
        this.repository.changeStateUser(id, role, state.equals(true) ? "1" : "0");
    }

    @Override
    public User updateUser(UserUpdateDTO dto, String id) {
        //Busca el Usuario
        var user = this.repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recurso no encontrado", "El usuario no fue encontrado para actualizarlo"
                    )
                );
        //Actualizo las propiedades solicitadas
        user.update(dto, encoder);

        //Guardo la Entidad y mapeo a un User normal
        return this.userMapper.userEntityToUser().map(this.repository.saveAndFlush(user));
    }
}
