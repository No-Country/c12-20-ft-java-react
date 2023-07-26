package c1220ftjavareact.gym.user.repository;

import c1220ftjavareact.gym.user.entity.ForgotPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Long> {
    Optional<ForgotPasswordEntity> findByCode(String code);

    Optional<ForgotPasswordEntity> findByUserEntityEmail(String email);

    Boolean existsByUserEntityEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO forgot_password (id_user, code, enable, expiration_date) VALUES " +
            "(:id, :code, :enable, :expirationDate)", nativeQuery = true)
    void saveForgotPassword(
            @Param("id") String id,
            @Param("code") String code,
            @Param("enable") Integer enable,
            @Param("expirationDate") LocalDateTime expirationDate
    );

    @Modifying
    @Query(value = "UPDATE forgot_password AS p SET p.enable = '0' WHERE p.id_user = :id", nativeQuery = true)
    void disable(@Param("id") String id);
}
