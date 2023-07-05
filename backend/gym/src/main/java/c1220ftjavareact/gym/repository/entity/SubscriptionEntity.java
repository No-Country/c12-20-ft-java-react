package c1220ftjavareact.gym.repository.entity;

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
    int id;
    @NotNull
    int idClient;
    @NotNull
    int idClass;
    @NotNull
    State state;
    @NotNull
    @Column(name = "subscription_day")
    LocalDateTime subscriptionDay;
}
