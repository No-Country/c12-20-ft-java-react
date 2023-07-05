package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.repository.entity.User;

public interface AuthService {

    Boolean authenticateCredential(String email, String password);

    String getCredentialEmail(String token);

    Boolean isExpired(String token);

    String generateToken(User user);
}
