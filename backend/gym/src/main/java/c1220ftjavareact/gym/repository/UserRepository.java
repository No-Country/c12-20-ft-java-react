package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.domain.dto.UserProjection;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT u.id, u.email, concat(u.name,' ',u.lastname) AS fullName, u.role, u.avatar FROM user AS u WHERE u.email = :email", nativeQuery = true)
    UserProjection findUserForLogin(String email);

    @Query(value = "DELETE FROM user AS u WHERE u.id = :id AND u.role = :role", nativeQuery = true)
    void deleteUsersBy(String id, String role);

    @Query(value = "UPDATE user AS u SET u.deleted = '1' WHERE u.id = :id AND u.role = :role", nativeQuery = true)
    void userLogicalDeleteBy(String id, String role);
}
