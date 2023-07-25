package c1220ftjavareact.gym.payment.dto;

import c1220ftjavareact.gym.payment.paymentenum.Method;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaymentDtoComplete {
    private Long id;
    private Long idSubscription;
    private LocalDate day;
    private LocalDate expired;
    private Method method;
}
