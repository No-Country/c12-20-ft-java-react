package c1220ftjavareact.gym.subscription.entity;

import c1220ftjavareact.gym.subscription.model.State;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@Data
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_customer")
    UserEntity idCustomer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_training_session")
    TrainingSession training;
    @NotNull
    State state;
    @NotNull
    @Column(name = "subscription_day")
    LocalDateTime subscriptionDay;


}
