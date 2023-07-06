package c1220ftjavareact.gym.domain.dto;

import c1220ftjavareact.gym.domain.ForgotPassword;
import c1220ftjavareact.gym.domain.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForgotPasswordDTO {
    private ForgotPassword forgotPassword;
    private User user;


}
