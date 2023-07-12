package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.repository.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    @Query("SELECT ts.capacity FROM TrainingSession ts WHERE ts.id = :id")
    Integer findCapacityById(@Param("id") Long id);
}
