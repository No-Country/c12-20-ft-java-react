package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.repository.entity.ForgotPasswordEntity;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpdatePasswordRepository extends JpaRepository<ForgotPasswordEntity, UserEntity> {

    Boolean existsByCode(String code);

    Optional<ForgotPasswordEntity> findByCode(String code);
}
