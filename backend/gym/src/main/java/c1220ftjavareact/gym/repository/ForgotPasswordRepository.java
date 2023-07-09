package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.repository.entity.ForgotPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Long> {

    Boolean existsByCode(String code);

    Optional<ForgotPasswordEntity> findByCode(String code);

    Optional<ForgotPasswordEntity> findForgotPasswordEntityByUserEntityEmail(String email);
    Boolean existsForgotPasswordEntityByUserEntityEmail(String email);


    @Modifying
    @Query(value = "INSERT INTO forgot_password (id_user, code, expiration_date) VALUES " +
            "(:id, :code, :expirationDate)", nativeQuery = true)
    void saveForgotPassword(
            @Param("id") String id,
            @Param("code") String code,
            @Param("expirationDate") LocalDateTime expirationDate
    );
}
