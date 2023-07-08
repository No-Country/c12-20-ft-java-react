package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.domain.dto.UserProjection;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    //
   Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO user (name, email, lastname, password, role) VALUES " +
            "(:name, :email, :lastname, :password, :role)", nativeQuery = true)
    void saveUser(
            @Param("name") String name,
            @Param("email") String email,
            @Param("lastname") String lastname,
            @Param("password") String password,
            @Param("role") String role
    );

    @Query(value = "SELECT u.id, u.email, concat(u.name,' ',u.lastname) AS fullName, u.role, u.avatar FROM user AS u WHERE u.email = :email", nativeQuery = true)
    UserProjection findUserForLogin(@Param("email") String email);

    @Modifying
    @Query(value = "DELETE FROM user AS u WHERE u.id = :id AND u.role = :role", nativeQuery = true)
    void deleteUsersBy(@Param("id") String id, @Param("role") String role);

    @Modifying
    @Query(value = "UPDATE user AS u SET u.deleted = :state WHERE u.id = :id AND u.role = :role", nativeQuery = true)
    void changeStateUser(@Param("id") String id, @Param("role") String role, @Param("state") String state);

    @Query(value = "SELECT count(*) FROM user WHERE user.id = :id AND user.role = :role", nativeQuery = true)
    Integer countUsersBy(@Param("id") String id, @Param("role") String role);
    @Query(value = "SELECT count(*) FROM user WHERE user.role = 'ADMIN'", nativeQuery = true)
    Integer countAdmins();
}
