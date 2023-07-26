package c1220ftjavareact.gym.user.service;

import c1220ftjavareact.gym.user.dto.UserPasswordDTO;
import c1220ftjavareact.gym.user.model.ForgotPassword;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

public interface ForgotPasswordService {

    Long findIdByEmail(String email);

    @Transactional
    Map<String, String> saveForgot(String email);

    /**
     * Crea una nueva instancia de ForgotPassword
     *
     * @param email Email del usuario que solicita crear el Forgot Password
     */
    Map<String, String> saveForgotPassword(String email);

    /**
     * Genera una nueva instancia de Forggot password
     *
     * @param id    ID del Usuario que crea la instancia
     * @param email Email del usuario que crea la instancia
     */
    ForgotPassword generateForgotPassword(String id, String email);

    @Transactional
    Map<String, String> createOtherPassword(ForgotPassword forgotPassword);

    /**
     * Busca una instancia del ForgotPassword por el codigo
     *
     * @param code Codigo del ForgotPassword
     */
    ForgotPassword findByCode(String code);

    /**
     * Arroja una excepcion si los ID son distintos
     *
     * @param idModel ID que debe de ser igual
     * @param idSaved ID guardado en la base de datos
     */
    void assertKeysEquals(String idModel, String idSaved);

    /**
     * Arroja una excepcion si la fecha ya ha expirado
     *
     * @param dateTime Fecha a comprobar
     */
    void assertIsNotExpired(LocalDateTime dateTime, Long id);

    void assertIsExpired(LocalDateTime dateTime);

    /**
     * Arroja una excepcion si Enable es False
     *
     * @param enable Enable
     */
    void assertIsEnable(Boolean enable);

    void assertIsNotEnable(Boolean enable);

    /**
     * Actualiza el Forggot Password
     *
     * @param model Modelo con los datos para actualizar
     */
    void updateForgottenPassword(UserPasswordDTO model);

    ForgotPassword findByEmail(String email);

    /**
     * Verifica si existe uns instancia de Forgot passowrd con este email
     *
     * @param email Email a comprobar
     */
    Boolean existsByEmail(String email);
}
