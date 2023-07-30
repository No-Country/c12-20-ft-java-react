package c1220ftjavareact.gym.user.service;

import c1220ftjavareact.gym.security.exception.ResourceAlreadyExistsException;
import c1220ftjavareact.gym.security.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.training.exception.TrainingException;
import c1220ftjavareact.gym.user.dto.EmployeeDTO;
import c1220ftjavareact.gym.user.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.user.dto.UserSaveDTO;
import c1220ftjavareact.gym.user.dto.UserUpdateDTO;
import c1220ftjavareact.gym.user.dto.mapper.UserMapperBeans;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.user.enums.Role;
import c1220ftjavareact.gym.user.exception.UserSaveException;
import c1220ftjavareact.gym.user.model.User;
import c1220ftjavareact.gym.user.projection.UserProjection;
import c1220ftjavareact.gym.user.repository.UserRepository;
import c1220ftjavareact.gym.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
                    "Resource is already registered.", "The email is already in use."
            );
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(String id) {
        var user = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Resource not found.", "The user was not found."
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
                                "Resource not found.", "The user was not found."
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
                throw new UserSaveException("Error while saving the user.", "An admin is already registered, it's not possible to create two admins.");
            }
            repository.saveUser(model.name(), model.email(), model.lastname(), userMapper.password().map(model.password()), role);
        } catch (Exception ex) {
            throw new UserSaveException("Error while saving the user.", "An unexpected error occurred while saving the user.");
        }
    }

    @Transactional
    @Override
    public Map<String, String> saveEmployee(EmployeeSaveDTO model) {
        var values = new HashMap<String, String>();
        var pass = UUID.randomUUID().toString().substring(0, 6);
        values.put("pass", pass);
        try {
            var entity = new UserEntity();

            entity.setDeleted(false);
            entity.setName(model.name());
            entity.setEmail(model.email());
            entity.setRole(Role.EMPLOYEE);
            entity.setLastname(model.lastname());
            entity.setPicture(model.picture());
            entity.setPassword(userMapper.password().map(pass));
            entity.setCreateAt(TimeUtils.getLocalDate());

            var user = repository.save(entity);
            values.put("Id", user.getId().toString());
        } catch (Exception ex) {
            throw new UserSaveException("Error while saving the user.", "An unexpected error occurred while saving the user.");
        }

        return values;
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
            throw new UserSaveException("Error while saving the user.", "An unexpected error occurred while saving the user.");
        }
    }

    @Transactional
    @Override
    public void changeDeletedStateUser(Set<Long> employeeIds) {
        //Cuanta la cantidad de usuarios que existe con ese ID y Rol
        //Cambia el estado del usuario
        this.repository.toggleDeletedStatusForEmployees(employeeIds);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<EmployeeDTO> findAllEmployees() {
        return this.repository.findAllEmployee();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<String> findActiveActivity(String id) {
        return this.repository.findActiveActivity(id);
    }


    @Override
    public User updateUser(UserUpdateDTO dto, String id) {
        //Busca el Usuario
        var user = this.repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Resource not found.", "The user was not found."
                        )
                );
        //Actualizo las propiedades solicitadas
        user.update(dto, encoder);

        //Guardo la Entidad y mapeo a un User normal
        return this.userMapper.userEntityToUser().map(this.repository.saveAndFlush(user));
    }

    @Override
    public UserEntity getUserEntity(Long id) {
        Optional<UserEntity> user = repository.findById(id);
        if(user.isEmpty()) {
            throw new TrainingException("User not found.", HttpStatus.NOT_FOUND);
        }

        return user.get();
    }
}
