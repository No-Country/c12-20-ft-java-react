package c1220ftjavareact.gym.user.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
public record UserSaveDTO(
        @NotEmpty @Size(min = 3, max = 45) String name,
        @NotEmpty @Size(min = 3, max = 45) String lastname,
        @Email @NotEmpty @Size(max = 128) String email,
        @NotEmpty @Size(min = 6, max = 128) String password
) {

}
