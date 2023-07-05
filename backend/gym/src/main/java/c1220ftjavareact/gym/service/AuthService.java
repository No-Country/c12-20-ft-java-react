package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.entity.User;

public interface AuthService {

    Boolean authenticateCredential(String email, String password);

    String getCredentialEmail(String token);

    Boolean isExpired(String token);

    String generateToken(User user);
}
