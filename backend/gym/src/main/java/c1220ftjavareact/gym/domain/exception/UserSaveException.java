package c1220ftjavareact.gym.domain.exception;

import lombok.Getter;

@Getter
public class UserSaveException extends ApiException {

    public UserSaveException(String message, String title) {
        super(message, title);
    }
}
