package c1220ftjavareact.gym.domain.exception;

public class UserSaveException extends RuntimeException {
    private final String resolve;

    public UserSaveException(String message, String resolve) {
        super(message);
        this.resolve = resolve;
    }
}
