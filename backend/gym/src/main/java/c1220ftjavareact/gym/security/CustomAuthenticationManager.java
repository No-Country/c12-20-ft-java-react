package c1220ftjavareact.gym.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Primary
@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserDetailsService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.isAuthenticated()) return authentication;

        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        try {
            var user = (UserDetails) this.service.loadUserByUsername(email);

            if (user == null)
                throw new UsernameNotFoundException("User not found");

            if(!this.encoder.matches(password, user.getPassword()))
                throw new BadCredentialsException("The password does not match the account password");

            var auth = UsernamePasswordAuthenticationToken.authenticated(
                    authentication.getPrincipal(),
                    authentication.getCredentials().toString(),
                    user.getAuthorities()
            );

            auth.setDetails(authentication.getDetails());
            return auth;
        } catch (Exception e) {
            throw new AuthenticationServiceException("Error authenticating user, error: ", e);
        }
    }
}
