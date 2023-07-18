package c1220ftjavareact.gym.common;

import c1220ftjavareact.gym.common.ApiException;
import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends ApiException {

    public ResourceAlreadyExistsException(String message, String title) {
        super(message, title);
    }
}
