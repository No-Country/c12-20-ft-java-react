package c1220ftjavareact.gym.training.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TrainingException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public TrainingException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}