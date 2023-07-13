package c1220ftjavareact.gym.activity.repository;

import c1220ftjavareact.gym.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
