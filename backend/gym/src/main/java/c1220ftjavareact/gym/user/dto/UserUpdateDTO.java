package c1220ftjavareact.gym.user.dto;

import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public record UserUpdateDTO(
        @Email String email,
        @Size(min = 3, max = 45) String name,
        @Size(min = 3, max = 45) String lastName,
        @Size(min = 6, max = 128) String oldPassword,
        @Size(min = 6, max = 128) String updatedPassword,
        String picture
) {
    public UserUpdateDTO {
        if (
                (StringUtils.hasText(updatedPassword) && !StringUtils.hasText(oldPassword)) ||
                        (StringUtils.hasText(oldPassword) && !StringUtils.hasText(updatedPassword))
        ) {
            throw new IllegalArgumentException(
                    "Si deseas actualizar la contraseña, debs de enviar ambas"
            );
        }

    }
}
