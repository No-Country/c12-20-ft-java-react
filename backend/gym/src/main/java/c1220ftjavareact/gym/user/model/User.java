package c1220ftjavareact.gym.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
public final class User {
    private String id;
    private String email;
    private String name;
    private String lastname;
    private String role;
    private LocalDate createAt;
    private String password;
    private String picture;
    private Boolean deleted;


    public String fullname() {
        var fullname = new StringBuilder();
        fullname
                .append(this.getName().substring(0, 1).toUpperCase())
                .append(this.getName().substring(1))
                .append(" ")
                .append(this.getLastname().substring(0, 1).toUpperCase())
                .append(this.getLastname().substring(1));
        return fullname.toString();
    }
}
