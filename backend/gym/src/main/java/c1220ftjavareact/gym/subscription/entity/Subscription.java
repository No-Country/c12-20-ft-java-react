package c1220ftjavareact.gym.subscription.entity;

import c1220ftjavareact.gym.subscription.enums.State;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate createDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('RESERVED', 'ACTIVE', 'INACTIVE', 'CANCELED')")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private UserEntity idCustomer;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "training_session_id")
    private TrainingSession training;
}
