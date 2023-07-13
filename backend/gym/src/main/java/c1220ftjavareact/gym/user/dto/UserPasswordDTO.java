package c1220ftjavareact.gym.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record UserPasswordDTO(
        @NotEmpty @Size(min = 6, max = 128) String password,
        @NotEmpty @Size(min = 6, max = 128) String repeatedPassword,
        @NotEmpty @Size(max = 36) String code,
        @NotEmpty String id
) {
}
