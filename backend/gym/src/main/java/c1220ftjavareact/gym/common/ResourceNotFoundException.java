package c1220ftjavareact.gym.common;

import c1220ftjavareact.gym.common.ApiException;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message, String title) {
        super(message, title);
    }
}
