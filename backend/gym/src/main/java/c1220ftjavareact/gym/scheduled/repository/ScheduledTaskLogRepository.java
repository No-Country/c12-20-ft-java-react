package c1220ftjavareact.gym.scheduled.repository;

import c1220ftjavareact.gym.scheduled.entity.ScheduledTaskLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledTaskLogRepository extends JpaRepository<ScheduledTaskLog, Long> {

    ScheduledTaskLog findTopByOrderByLastExecutionDesc();
}
