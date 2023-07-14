package c1220ftjavareact.gym.payment.dto;

import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private SubscriptionDTO idSubscription;
    private LocalDate day;
    private LocalDate expired;

}
