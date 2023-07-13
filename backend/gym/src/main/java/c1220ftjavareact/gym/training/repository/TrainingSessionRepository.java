package c1220ftjavareact.gym.training.repository;

import c1220ftjavareact.gym.training.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
}
