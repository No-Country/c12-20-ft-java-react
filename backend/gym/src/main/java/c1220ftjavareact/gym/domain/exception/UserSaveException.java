package c1220ftjavareact.gym.domain.exception;

import lombok.Getter;

@Getter
public class UserSaveException extends RuntimeException {
    private final String resolve;

    public UserSaveException(String message, String resolve) {
        super(message);
        this.resolve = resolve;
    }
}
