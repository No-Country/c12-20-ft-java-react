package c1220ftjavareact.gym.payment.entity;


import c1220ftjavareact.gym.payment.paymentenum.Method;
import c1220ftjavareact.gym.subscription.entity.Subscription;
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
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Subscription subscriptionId;
    @Column(name = "payment_at")
    private LocalDate paymentAt;
    @Column(name = "expired_at")
    private LocalDate expiredAt;
    @Enumerated(EnumType.STRING)
    private Method method;
}
