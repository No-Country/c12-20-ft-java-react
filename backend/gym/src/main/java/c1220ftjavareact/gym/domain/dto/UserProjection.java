package c1220ftjavareact.gym.domain.dto;

import c1220ftjavareact.gym.repository.entity.Role;

public interface UserProjection {
    String getId();
    String getEmail();
    String getFullname();
    Role getRole();
    String getAvatar();
}
