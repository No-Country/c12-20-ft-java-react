package c1220ftjavareact.gym.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
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
}
