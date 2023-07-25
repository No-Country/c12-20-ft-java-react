package c1220ftjavareact.gym.subscription.dto;

import c1220ftjavareact.gym.subscription.enums.State;
import lombok.Data;

@Data
public class SubscriptionUpdateDTO {
    private Long id;
    private State updatedState;
}
