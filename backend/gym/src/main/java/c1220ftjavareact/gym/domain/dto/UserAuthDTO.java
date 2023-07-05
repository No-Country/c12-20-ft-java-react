package c1220ftjavareact.gym.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record UserAuthDTO(
        @Email @NotEmpty String email,
        @NotEmpty String password
) {
}
