package c1220ftjavareact.gym.domain.dto;

import c1220ftjavareact.gym.repository.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubscriptionDTO {
    private Long idClient;
    private Long idClass;
    private State state;
    private LocalDateTime subscriptionDay;
    private Long idTrainingSession;
}
