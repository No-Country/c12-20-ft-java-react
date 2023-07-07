package c1220ftjavareact.gym.domain.dto;

import c1220ftjavareact.gym.repository.entity.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserProjection {
    private String id;
    private String email;
    private String fullname;
    private Role role;
    private String avatar;
}
