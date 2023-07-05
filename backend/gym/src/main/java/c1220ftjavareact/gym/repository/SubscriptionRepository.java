package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.repository.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {

}
