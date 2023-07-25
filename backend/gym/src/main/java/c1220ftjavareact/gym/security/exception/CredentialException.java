package c1220ftjavareact.gym.security.exception;

import lombok.Getter;

@Getter
public class CredentialException extends ApiException {

    public CredentialException(String message, String title) {
        super(message, title);
    }
}
