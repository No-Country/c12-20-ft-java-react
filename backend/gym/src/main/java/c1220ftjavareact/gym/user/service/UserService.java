package c1220ftjavareact.gym.user.service;

import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.user.dto.EmployeeDTO;
import c1220ftjavareact.gym.user.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.user.dto.UserSaveDTO;
import c1220ftjavareact.gym.user.dto.UserUpdateDTO;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.user.model.User;
import c1220ftjavareact.gym.user.projection.UserProjection;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

public interface UserService {
    /**
     * Busca el usuario por el ID
     *
     * @param id ID del usuario buscado
     */
    User findUserById(String id);

    /**
     * Busca el usuario por el Email
     *
     * @param email Email del usuario buscado
     */
    User findUserByEmail(String email);

    /**
     * Recupera el Usuario con los datos necesarios para el Login
     *
     * @param email Email del usuario
     */
    UserProjection findLoginInfo(String email);

    /**
     * Verifica que el correo no este registrado
     *
     * @param email Email a verificar
     */
    void assertEmailIsNotRegistered(String email);

    /**
     * Guarda un Usuario en la base de datos
     *
     * @param model Modelo del usuario con sus datos
     * @param role  Rol del usuario que se desea guardar
     */
    void saveUser(UserSaveDTO model, String role);

    /**
     * Guarda un empleado
     *
     * @param model Modelo con los datos del empleado
     * @return Contiene la contraseña y el ID del empleado
     */
    Map<String, String> saveEmployee(EmployeeSaveDTO model);

    /**
     * Guarda un Usuario registrado con google en la base de datos
     *
     * @param model Modelo del usuario con sus datos
     */
    void saveGoogleUser(User model);

    /**
     * Cambia el estado de eliminacion de un Usuario
     * <p>
     * employeeIds Son los ID de los usuarios que se desea cambiar el estado
     */
    void changeDeletedStateUser(Set<Long> employeeIds);

    @Transactional(readOnly = true)
    Set<EmployeeDTO> findAllEmployees();

    @Transactional(readOnly = true)
    Set<String> findActiveActivity(String id);

    /**
     * Actualiza el Usuario con nuevos datos
     *
     * @param dto Modelo con los nuevos datos
     * @param id  ID del usuario que se desea actualizar
     */
    User updateUser(UserUpdateDTO dto, String id);

    UserEntity getUserEntity(Long id);
}
