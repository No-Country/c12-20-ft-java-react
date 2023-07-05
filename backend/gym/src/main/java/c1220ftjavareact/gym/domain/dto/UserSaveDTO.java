package c1220ftjavareact.gym.domain.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
public record UserSaveDTO(
        @NotEmpty String name,
        @NotEmpty String lastname,
        @Email String email,
        String password
) {

}
