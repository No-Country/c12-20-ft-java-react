package c1220ftjavareact.gym.repository.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "forgot_password")
@Slf4j
public class ForgotPasswordEntity {

    @Id
    @Column(name = "id_user")
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "enable", nullable = false)
    private boolean enable;

    @Column(name = "expiration_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    private LocalDateTime expirationDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private UserEntity userEntity;

    public void enable() {
        this.setEnable(true);
    }

    public void disable() {
        this.setEnable(false);
    }

    public void changeRamdomCode() {
        this.setCode(UUID.randomUUID().toString());
    }

    public Boolean isExpired(LocalDateTime dateTime) {
        return dateTime.isBefore( LocalDateTime.now(Clock.system(ZoneId.systemDefault())) );
    }
}
