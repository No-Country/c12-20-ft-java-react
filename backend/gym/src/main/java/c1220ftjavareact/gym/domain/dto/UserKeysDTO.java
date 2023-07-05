package c1220ftjavareact.gym.domain.dto;

import lombok.Builder;

@Builder
public record UserKeysDTO(String id, String role, String token) {
}
