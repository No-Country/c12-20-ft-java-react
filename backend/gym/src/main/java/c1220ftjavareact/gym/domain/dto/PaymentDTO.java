package c1220ftjavareact.gym.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private int idSubscription;
    private LocalDateTime day;
    private LocalDateTime expired;

}
