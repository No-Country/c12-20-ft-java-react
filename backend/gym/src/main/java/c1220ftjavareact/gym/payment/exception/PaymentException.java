package c1220ftjavareact.gym.payment.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PaymentException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public PaymentException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
