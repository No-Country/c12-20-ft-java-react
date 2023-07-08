package c1220ftjavareact.gym.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("google")
@Component
@RequiredArgsConstructor
public class GoogleAuthService implements AuthService{
    @Override
    public void authenticate(String principal, String credential) {

    }
}
