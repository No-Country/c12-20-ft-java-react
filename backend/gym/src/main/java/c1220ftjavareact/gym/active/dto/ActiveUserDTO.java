package c1220ftjavareact.gym.active.dto;

import c1220ftjavareact.gym.user.enums.Role;
import lombok.Data;

@Data
public class ActiveUserDTO {

    private Long id;

    private Role role;

    private String email;

    private String picture;

    private String fullName;

    private String token;
}
