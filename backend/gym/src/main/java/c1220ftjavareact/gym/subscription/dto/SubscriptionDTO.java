package c1220ftjavareact.gym.subscription.dto;


import c1220ftjavareact.gym.subscription.model.State;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
public record SubscriptionDTO(
        @NotNull @Size(min = 1, max = 45) Long customerId,
        @NotNull @Size(min = 1, max = 45) Long training,
        @NotNull State state,
        @NotNull LocalDate subscriptionDay,
        @NotNull @Size(min = 1, max = 45) Long idTrainingSession
) {
}
