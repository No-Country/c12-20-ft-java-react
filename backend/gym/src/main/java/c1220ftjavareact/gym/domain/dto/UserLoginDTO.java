package c1220ftjavareact.gym.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
public class UserLoginDTO {
    private String id;
    private String email;
    private String fullname;
    private String role;
    private String token;

    public void token(String token) {
        this.setToken(token);
    }

}
