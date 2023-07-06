package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.exception.CredentialException;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.security.jwt.JwtService;
import c1220ftjavareact.gym.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringAuthService implements AuthService {
    private final AuthenticationManager manager;
    private final JwtService<UserDetails> jwtService;
    private final UserMapperBeans mapper;

    /**
     * Autentica las credenciales para validar el usuario
     *
     * @param email    Email de la cuenta del usuario
     * @param password Contraseña de la cuenta del usuario
     * @return True en caso de valdio, False en caso de invalido
     */
    @Override
    public Boolean authenticateCredential(String email, String password) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(
                    email,
                    password
            ));
            return true;
        } catch (Exception ex) {
            throw new CredentialException(
                    "Las credenciales no son autenticas",
                    ex.getLocalizedMessage(),
                    "Credenciales: "+email+" y "+password);
        }
    }

    /**
     * Recupera el email que esta guardado en el token
     *
     * @param token Token JWT
     * @return Email del token
     */
    @Override
    public String getCredentialEmail(String token) {
        return jwtService.extractSubject(token);
    }

    /**
     * Comprueba si el token ha caducado
     *
     * @param token Token JWT
     */
    @Override
    public Boolean isExpired(String token) {
        return jwtService.isTokenExpired(token);
    }

    /**
     * Genera un nuevo token
     *
     * @param user Usuario que generara el token
     */
    @Override
    public String generateToken(User user) {
        return jwtService.generateToken(mapper.userToUserDetails().map(user));
    }
}
