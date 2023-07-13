package c1220ftjavareact.gym.domain.exception;

import lombok.Getter;

@Getter
public class UpdatePasswordException extends ApiException {

    public UpdatePasswordException(String message, String title) {
        super(message, title);
    }
}
