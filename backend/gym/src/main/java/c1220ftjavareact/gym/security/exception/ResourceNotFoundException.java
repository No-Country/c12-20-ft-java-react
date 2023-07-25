package c1220ftjavareact.gym.security.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message, String title) {
        super(message, title);
    }
}
