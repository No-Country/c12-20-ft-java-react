package c1220ftjavareact.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
public class User {
    private final String id;
    private final String email;
    private final String name;
    private final String lastname;
    private final String role;
    private final LocalDate createAt;
    private final String password;
    private final String avatar;


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
