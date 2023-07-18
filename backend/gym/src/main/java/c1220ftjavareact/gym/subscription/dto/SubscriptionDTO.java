package c1220ftjavareact.gym.subscription.dto;

import c1220ftjavareact.gym.subscription.model.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private Long idClient;
    private Long idClass;
    private State state;
    private LocalDateTime subscriptionDay;
}
