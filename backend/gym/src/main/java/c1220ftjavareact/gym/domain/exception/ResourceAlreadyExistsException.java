package c1220ftjavareact.gym.domain.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends ApiException {

    public ResourceAlreadyExistsException(String message, String title) {
        super(message, title);
    }
}
