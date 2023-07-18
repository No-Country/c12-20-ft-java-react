package c1220ftjavareact.gym.user.exception;

import c1220ftjavareact.gym.common.ApiException;
import lombok.Getter;

@Getter
public class UserSaveException extends ApiException {

    public UserSaveException(String message, String title) {
        super(message, title);
    }
}
