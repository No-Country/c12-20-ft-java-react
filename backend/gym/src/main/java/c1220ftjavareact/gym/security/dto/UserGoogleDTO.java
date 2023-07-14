package c1220ftjavareact.gym.security.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
public record UserGoogleDTO(
        @NotEmpty @Email String email,
        @NotEmpty String name,
        @NotEmpty String lastName,
        @NotEmpty String picture
) {
}
