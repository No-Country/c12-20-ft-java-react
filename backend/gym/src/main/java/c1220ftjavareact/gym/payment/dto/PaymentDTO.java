package c1220ftjavareact.gym.payment.dto;

import c1220ftjavareact.gym.payment.paymentenum.Method;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long subscriptionId;
    private LocalDate paymentAt;
    private LocalDate expiredAt;
    private Method method;

}
