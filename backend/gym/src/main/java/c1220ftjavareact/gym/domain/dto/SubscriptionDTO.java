package c1220ftjavareact.gym.domain.dto;

import c1220ftjavareact.gym.repository.entity.State;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private int idClient;
    private int idClass;
    private State state;
    private LocalDateTime subscriptionDay;
}
