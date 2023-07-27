package c1220ftjavareact.gym.payment.repository;

import c1220ftjavareact.gym.payment.dto.PaymentDTO;
import c1220ftjavareact.gym.payment.dto.PaymentProjectionDto;
import c1220ftjavareact.gym.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    @Query(value = "SELECT p.subscription_id AS subscriptionId, p.method, p.payment_at AS paymentAt, p.expired_at AS expiredAt FROM payment AS p WHERE p.subscription_id = :id",nativeQuery = true)
    public List<PaymentProjectionDto> getPaymentWithSubscription(@Param("id") Long id);
}
