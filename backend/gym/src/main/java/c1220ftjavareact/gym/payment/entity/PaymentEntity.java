package c1220ftjavareact.gym.payment.entity;


import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "id_subscription", referencedColumnName = "id")
    @NotNull
    SubscriptionEntity idSubscription;
    @NotNull
    LocalDate day;
    @NotNull
    LocalDate expired;
}
