package c1220ftjavareact.gym.domain.exception;

import lombok.Builder;

@Builder
public record ErrorDTO(String message, String resolve, String target, String status) {
}
