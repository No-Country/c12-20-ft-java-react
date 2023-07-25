package c1220ftjavareact.gym.user.dto;

import c1220ftjavareact.gym.user.enums.Role;

public interface EmployeeDTO {
    Long getId();

    String getFullName();

    String getEmail();

    Role getRole();

    String getPicture();

    Boolean getDeleted();

}
