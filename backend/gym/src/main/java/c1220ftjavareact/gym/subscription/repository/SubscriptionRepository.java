package c1220ftjavareact.gym.subscription.repository;

import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @Query("SELECT COUNT(s) FROM SubscriptionEntity s WHERE s.training.id = :trainingSessionId")
    Integer countByTrainingSessionId(@Param("trainingSessionId") Long trainingSessionId);



}
