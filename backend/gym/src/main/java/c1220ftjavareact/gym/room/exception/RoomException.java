package c1220ftjavareact.gym.room.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RoomException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public RoomException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
