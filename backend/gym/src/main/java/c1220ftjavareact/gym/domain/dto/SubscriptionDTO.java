package c1220ftjavareact.gym.domain.dto;

import c1220ftjavareact.gym.repository.entity.State;
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
