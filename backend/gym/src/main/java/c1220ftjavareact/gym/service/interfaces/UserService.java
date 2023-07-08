package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.*;

public interface UserService {
    User findUserById(String id);
    User findUserByEmail(String email);
    /**
     * Recupera el objeto con los datos necesarios para el Login
     * @param email Email del usuario
     */
    UserProjection findLoginInfo(String email);

    /**
     * Verifica que el correo no este registrado
     * @param email Email a verificar
     */
    void assertEmailIsNotRegistered(String email);

    /**
     * Guarda un Usuario en la base de datos
     * @param model Modelo del usuario para guardar
     * @param role Rol del usuario que se desea guardar
     */
    void saveUser(UserSaveDTO model, String role);

    /**
     * Guarda un Admin en la base de datos
     * @param model Modelo del usuario para guardar
     */
    void saveAdmin(UserSaveDTO model);

    /**
     * Cambia el estado de eliminacion de un Usuario
     * @param id ID del usuario que se desea guardar
     * @param role Rol del usuario
     * @param state Estado al que se desea cambiar
     */
    void changeDeletedStateUser(String id, String role, Boolean state);

    User updateUser(UserUpdateDTO dto, String id);
}
