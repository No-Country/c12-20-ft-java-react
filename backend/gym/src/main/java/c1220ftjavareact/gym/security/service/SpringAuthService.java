package c1220ftjavareact.gym.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Qualifier(value = "spring")
@Component
@RequiredArgsConstructor
public class SpringAuthService implements AuthService {
    private final AuthenticationManager manager;

    /**
     * Autentica las credenciales para validar el usuario
     *
     * @param principal  Email de la cuenta del usuario
     * @param credential Contraseña de la cuenta del usuario
     * @return True en caso de valdio, False en caso de invalido
     */
    @Override
    public void authenticate(String principal, String credential) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                principal,
                credential
        ));
    }

}
