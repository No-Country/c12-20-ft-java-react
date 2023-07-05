package c1220ftjavareact.gym.service.interfaces;

public interface UpdatePasswordService {

    /*
     - Generar codigo
     - Enviar email
     - Guardar codigo, fecha y usuario que quiere actualizar; codigo debe de ser unico
     - Enviar una respuesta correcta que el email es valido y se pudo mandar
     - Front debe mostrar mensaje de Correo enviado y en caso de no recibirlo volver a probar

     - El usuario que debe actulizar tiene que pasar el codigo
     - En caso de ser igual
     - Permitir entrar a la URL

     - Al enviar la nueva contraseña
     - Marcar como vencida la fecha anterior

     */

    Boolean verifyEmail(String email);

    Boolean updatePasswordEvent(String email);

    void checkEmail(String code, String id);
}
