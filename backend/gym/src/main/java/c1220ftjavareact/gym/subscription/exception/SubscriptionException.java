package c1220ftjavareact.gym.subscription.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SubscriptionException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public SubscriptionException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
