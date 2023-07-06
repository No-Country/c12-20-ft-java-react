package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.repository.entity.ForgotPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Long> {

    Boolean existsByCode(String code);

    Optional<ForgotPasswordEntity> findByCode(String code);

    Optional<ForgotPasswordEntity> findForgotPasswordEntityByUserEntityEmail(String email);
    Boolean existsForgotPasswordEntityByUserEntityEmail(String email);
}
