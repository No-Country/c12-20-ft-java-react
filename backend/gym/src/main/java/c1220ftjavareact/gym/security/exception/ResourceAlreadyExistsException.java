package c1220ftjavareact.gym.security.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends ApiException {

    public ResourceAlreadyExistsException(String message, String title) {
        super(message, title);
    }
}
