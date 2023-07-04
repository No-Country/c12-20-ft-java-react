package c1220ftjavareact.gym.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record UserSaveDTO(
        @NotEmpty String name,
        @NotEmpty String lastname,
        @Email String email,
        String password
) {

}
