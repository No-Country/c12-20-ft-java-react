package c1220ftjavareact.gym.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long idSubscription;
    private LocalDate day;
    private LocalDate expired;

}