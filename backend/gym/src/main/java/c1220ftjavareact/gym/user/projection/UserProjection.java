package c1220ftjavareact.gym.user.projection;

import c1220ftjavareact.gym.user.enums.Role;

public interface UserProjection {
    String getId();

    String getEmail();

    String getFullName();

    Role getRole();

    String getPicture();
}
