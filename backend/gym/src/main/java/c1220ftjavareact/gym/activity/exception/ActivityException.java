package c1220ftjavareact.gym.activity.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class ActivityException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ActivityException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
