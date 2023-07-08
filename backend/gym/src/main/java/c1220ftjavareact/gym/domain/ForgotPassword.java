package c1220ftjavareact.gym.domain;

import lombok.Builder;
import lombok.Data;
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
