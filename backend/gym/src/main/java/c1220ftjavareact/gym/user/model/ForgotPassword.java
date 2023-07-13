package c1220ftjavareact.gym.user.model;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public record ForgotPassword(
        String id,
        String email,
        String code,
        @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm") LocalDateTime expirationDate,
        Boolean enable
) {

}
