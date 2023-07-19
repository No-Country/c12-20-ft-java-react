package c1220ftjavareact.gym.training.repository;

import c1220ftjavareact.gym.training.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByDeletedFalse();

    @Modifying
    @Query(value = "UPDATE training_session SET deleted=true WHERE ID=:id", nativeQuery = true)
    public void deleteSession(@Param("id") Long id);
=======

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
}
