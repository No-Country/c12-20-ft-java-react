package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.repository.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
