package c1220ftjavareact.gym.payment.repository;

import c1220ftjavareact.gym.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}
