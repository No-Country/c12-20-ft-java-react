package c1220ftjavareact.gym.domain.exception;

import lombok.Getter;

@Getter
public class CredentialException extends ApiException {

    public CredentialException(String message, String title) {
        super(message, title);
    }
}
