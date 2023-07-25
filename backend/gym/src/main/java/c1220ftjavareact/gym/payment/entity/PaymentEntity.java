package c1220ftjavareact.gym.payment.entity;


import c1220ftjavareact.gym.payment.paymentenum.Method;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_subscription", referencedColumnName = "id")
    @NotNull
    private SubscriptionEntity idSubscription;
    @NotNull
    private LocalDate day;
    @NotNull
    private LocalDate expired;
    @Enumerated(EnumType.STRING)
    private Method method;
}
