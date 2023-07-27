package c1220ftjavareact.gym.active.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ActiveUserException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ActiveUserException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}