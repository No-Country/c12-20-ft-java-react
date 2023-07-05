package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.entity.User;
import c1220ftjavareact.gym.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringAuthService implements AuthService{
    private final AuthenticationManager manager;
    private final JwtService<UserDetails> jwtService;

    @Override
    public Boolean authenticateCredential(String email, String password) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(
                    email,
                    password
            ));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String getCredentialEmail(String token) {
        return jwtService.extractSubject(token);
    }

    @Override
    public Boolean isExpired(String token) {
        return jwtService.isTokenExpired(token);
    }

    @Override
    public String generateToken(User user) {
        return jwtService.generateToken(user);
    }
}
