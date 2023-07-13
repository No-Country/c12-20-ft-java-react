package c1220ftjavareact.gym.user.service;

import c1220ftjavareact.gym.user.model.User;
import c1220ftjavareact.gym.user.projection.UserProjection;
import c1220ftjavareact.gym.user.dto.UserSaveDTO;
import c1220ftjavareact.gym.user.dto.UserUpdateDTO;

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
     * Guarda un Usuario registrado con google en la base de datos
     *
     * @param model Modelo del usuario con sus datos
     */
    void saveGoogleUser(User model);

    /**
     * Cambia el estado de eliminacion de un Usuario
     *
     * @param id    ID del usuario que se desea guardar
     * @param role  Rol del usuario
     * @param state Estado al que se desea cambiar
     */
    void changeDeletedStateUser(String id, String role, Boolean state);

    /**
     * Actualiza el Usuario con nuevos datos
     *
     * @param dto Modelo con los nuevos datos
     * @param id  ID del usuario que se desea actualizar
     */
    User updateUser(UserUpdateDTO dto, String id);
}
