package c1220ftjavareact.gym.domain.dto;

import c1220ftjavareact.gym.repository.entity.State;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import lombok.Builder;
import lombok.NonNull;

import c1220ftjavareact.gym.repository.entity.State;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record SubscriptionDTO(
        @NotNull @Size(min = 1, max = 45) Long customerId,
        @NotNull @Size(min = 1, max = 45) Long training,
        @NotNull State state,
        @NotNull LocalDate subscriptionDay,
        @NotNull @Size(min = 1, max = 45) Long idTrainingSession
) {}
