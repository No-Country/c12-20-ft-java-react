package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
}
