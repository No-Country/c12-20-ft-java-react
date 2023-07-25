package c1220ftjavareact.gym.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//
public record EmployeeSaveDTO(
        @NotEmpty @Size(min = 3, max = 45) String name,
        @NotEmpty @Size(min = 3, max = 45) String lastname,
        @NotEmpty String picture,
        @Email @NotEmpty @Size(max = 128) String email
) {
}
