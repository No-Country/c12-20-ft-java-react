package c1220ftjavareact.gym.subscription.entity;

import c1220ftjavareact.gym.room.entity.Room;
import c1220ftjavareact.gym.subscription.model.State;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscription")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    UserEntity idCustomer;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "training_session_id")
    TrainingSession training;

    @NotNull
    State state;
    @NotNull
    @Column(name = "create_date")
    LocalDate subscriptionDay;


}
