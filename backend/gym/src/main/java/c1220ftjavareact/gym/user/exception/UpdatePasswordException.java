package c1220ftjavareact.gym.user.exception;

import c1220ftjavareact.gym.security.exception.ApiException;
import lombok.Getter;

@Getter
public class UpdatePasswordException extends ApiException {

    public UpdatePasswordException(String message, String title) {
        super(message, title);
    }
}
