package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.repository.entity.UpdatePassword;
import c1220ftjavareact.gym.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpdatePasswordRepository extends JpaRepository<UpdatePassword, User> {
    Optional<UpdatePassword> findByUserEmail(String email);

    Boolean existsByCode(String code);
}
