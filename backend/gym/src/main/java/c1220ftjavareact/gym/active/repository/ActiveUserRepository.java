package c1220ftjavareact.gym.active.repository;

import c1220ftjavareact.gym.active.entity.ActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveUserRepository extends JpaRepository<ActiveUser, Long> {

    Optional<ActiveUser> findByUserId(Long userId);
}
