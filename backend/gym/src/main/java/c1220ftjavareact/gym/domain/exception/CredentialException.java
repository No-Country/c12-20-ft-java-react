package c1220ftjavareact.gym.domain.exception;

import lombok.Getter;

@Getter
public class CredentialException extends RuntimeException {
    private final String resolve;
    private final String target;

    public CredentialException(String message, String resolve, String target) {
        super(message);
        this.resolve = resolve;
        this.target = target;
    }
}
