package c1220ftjavareact.gym.user.repository;

import c1220ftjavareact.gym.user.dto.EmployeeDTO;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.user.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

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

    @Query(value = "SELECT u.id, u.email, concat(u.name,' ',u.lastname) AS fullName, u.role, u.picture FROM user AS u WHERE u.email = :email", nativeQuery = true)
    UserProjection findUserForLogin(@Param("email") String email);

    @Modifying
    @Query(value = "DELETE FROM user AS u WHERE u.id = :id AND u.role = :role", nativeQuery = true)
    void deleteUsersBy(@Param("id") String id, @Param("role") String role);

    @Modifying
    @Query(value = "UPDATE user AS u SET u.deleted = :state WHERE u.id = :id AND u.role = :role", nativeQuery = true)
    void changeStateUser(@Param("id") String id, @Param("role") String role, @Param("state") String state);

    @Modifying
    @Query("UPDATE UserEntity u SET u.deleted = CASE WHEN u.deleted = true THEN false ELSE true END WHERE u.id IN :userIds AND u.role = 'EMPLOYEE'")
    void toggleDeletedStatusForEmployees(Set<Long> userIds);

    @Query(value = "SELECT count(*) FROM user WHERE user.id = :id AND user.role = :role", nativeQuery = true)
    Integer countUsersBy(@Param("id") String id, @Param("role") String role);

    @Query(value = "SELECT count(*) FROM user WHERE user.role = 'ADMIN'", nativeQuery = true)
    Integer countAdmins();

    @Query(value = "SELECT u.id, concat(u.name,' ',u.lastname) AS fullName, u.email, u.role, u.picture, u.deleted FROM user AS u WHERE u.role = 'EMPLOYEE'", nativeQuery = true)
    Set<EmployeeDTO> findAllEmployee();

    @Query(value = "SELECT a.name AS activity FROM user AS u " +
            "JOIN subscription s ON  u.id = s.customer_id " +
            "JOIN training_session ts ON s.training_session_id = ts.id " +
            "JOIN activity a ON ts.activity_id = a.id " +
            "WHERE u.id = :id AND (s.state = 'ACTIVE' OR s.state = 'RESERVED')", nativeQuery = true)
    Set<String> findActiveActivity(@Param("id") String id);
}
