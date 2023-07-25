package c1220ftjavareact.gym.activity.repository;

import c1220ftjavareact.gym.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findAllActivityByDeletedFalse();

    @Query(value = "SELECT * FROM activity a WHERE a.deleted = false AND a.id = :id", nativeQuery = true)
    Activity findActivityFalse(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE activity SET deleted=true WHERE id=:id", nativeQuery = true)
    void deleteActivity(@Param("id") Long id);
}
