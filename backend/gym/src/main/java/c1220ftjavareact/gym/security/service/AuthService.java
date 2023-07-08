package c1220ftjavareact.gym.security.service;

import c1220ftjavareact.gym.domain.User;

public interface AuthService {

    void authenticateCredential(String email, String password);

    String getCredentialEmail(String token);

    Boolean isExpired(String token);

    String generateToken(User user);
}
