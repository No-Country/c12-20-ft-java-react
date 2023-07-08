package c1220ftjavareact.gym.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
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
        if (authentication.isAuthenticated()) {
            return authentication;
        }
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        try {
            var user = this.service.loadUserByUsername(email);

            if (!this.encoder.matches(password, user.getPassword()))
                throw new BadCredentialsException("The password does not match the account password");

            var auth = UsernamePasswordAuthenticationToken.authenticated(
                    email,
                    user.getPassword(),
                    user.getAuthorities()
            );

            auth.setDetails(authentication.getDetails());
            return auth;
        } catch (AuthenticationServiceException ex){
            ex.printStackTrace();
            throw new AuthenticationServiceException(ex.getLocalizedMessage());
        }
    }
}
