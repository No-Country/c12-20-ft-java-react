package c1220ftjavareact.gym.security.filter;

import c1220ftjavareact.gym.security.exception.CredentialException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Primary
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserDetailsService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(@NotNull Authentication authentication) throws AuthenticationException {
        //Si ya esta autenticado no se hace nada
        if (authentication.isAuthenticated()) {
            return authentication;
        }
        try {
            String email = authentication.getPrincipal().toString();
            var user = this.service.loadUserByUsername(email);

            //Validaciones
            this.verifyEnable(user.isEnabled());
            String password = authentication.getCredentials().toString();
            if (!password.equals("google")) this.verifyPasswords(password, user.getPassword());

            //Si recupero el usuario y es valido lo autentica
            var auth = UsernamePasswordAuthenticationToken.authenticated(
                    email,
                    user.getPassword(),
                    user.getAuthorities()
            );
            auth.setDetails(authentication.getDetails());
            return auth;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CredentialException("Authentication error.", "An error occurred while authenticating the user's credentials.");
        }
    }

    /**
     * Verifica que el estado de activacion (Es el mismo que el de eliminacion) sea Activo
     *
     * @param enable Estado de activacion
     */
    private void verifyEnable(Boolean enable) {
        if (!enable)
            throw new CredentialException("Authentication error.", "The user trying to access is disabled.");
    }

    /**
     * Verifica que la contraseña sin codificar y la guardada sean iguales
     *
     * @param rawPassword     Contraseña sin codificar
     * @param encodedPassword Contraseña Condificada
     */
    private void verifyPasswords(String rawPassword, String encodedPassword) {
        if (!this.encoder.matches(rawPassword, encodedPassword))
            throw new CredentialException("Authentication error.", "The password does not belong to the user.");
    }
}
