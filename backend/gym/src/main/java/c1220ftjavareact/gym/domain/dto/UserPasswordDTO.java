package c1220ftjavareact.gym.domain.dto;

import javax.validation.constraints.NotEmpty;

public record UserPasswordDTO(
        @NotEmpty String password,
        @NotEmpty String repeatedPassword,
        @NotEmpty String code,
        @NotEmpty String id
) {
}
