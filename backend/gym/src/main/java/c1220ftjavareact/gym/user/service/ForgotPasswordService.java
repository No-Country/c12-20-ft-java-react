package c1220ftjavareact.gym.user.service;

import c1220ftjavareact.gym.user.model.ForgotPassword;
import c1220ftjavareact.gym.user.dto.UserPasswordDTO;

import java.time.LocalDateTime;
import java.util.Map;

public interface ForgotPasswordService {

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
    void assertIsNotExpired(LocalDateTime dateTime);

    /**
     * Arroja una excepcion si Enable es False
     *
     * @param enable Enable
     */
    void assertIsEnable(Boolean enable);

    /**
     * Actualiza el Forggot Password
     *
     * @param model Modelo con los datos para actualizar
     */
    void updateForgottenPassword(UserPasswordDTO model);

    /**
     * Busca un instancia de ForgotPassword por el Email
     *
     * @param email Email a comprobar
     */
    ForgotPassword findByEmail(String email);

    /**
     * Verifica si existe uns instancia de Forgot passowrd con este email
     *
     * @param email Email a comprobar
     */
    Boolean existsByEmail(String email);
}