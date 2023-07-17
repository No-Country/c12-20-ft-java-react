package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.user.entity.ForgotPasswordEntity;
import c1220ftjavareact.gym.user.enums.Role;
import c1220ftjavareact.gym.user.repository.ForgotPasswordRepository;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.user.repository.UserRepository;
import c1220ftjavareact.gym.util.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ForgotPassRepositoryUnitTest {
    @Autowired
    private ForgotPasswordRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    private UserEntity user;
    private ForgotPasswordEntity entity;

    @BeforeEach
    public void setUser() {
        // Preparar los datos de prueba
        this.user = userRepository.save(UserEntity.builder()
                .name("Junito")
                .lastname("Remirez")
                .email("jr123@gmail.com")
                .password("1234567")
                .role(Role.CUSTOMER)
                .picture("default")
                .createAt(LocalDate.now())
                .deleted(false)
                .build()
        );
        this.entity =repository.save(ForgotPasswordEntity.builder()
                .id(user.getId())
                .userEntity(user)
                .code(UUID.randomUUID().toString())
                .expirationDate(TimeUtils.gerFormatedLocalDateTime().plusHours(1))
                .enable(true)
                .build()
        );
    }

    @DisplayName(value = "Test de saveForgot")
    @Test
    protected void testSaveUser(){
        entityManager.clear();
        user.setEmail("test@gmail.com");
        user.setId(null);
        var user2 = userRepository.save(user);
        assertDoesNotThrow(()->
                this.repository.saveForgotPassword(
                        user2.getId().toString(), UUID.randomUUID().toString(), "1", TimeUtils.gerFormatedLocalDateTime().plusHours(1)
                )
        );

    }

    @DisplayName(value = "Test de existsByEmail del ForgotEntity")
    @Test
    public void testForgotExistsByEmail(){
        var isExists = repository.existsByUserEntityEmail(user.getEmail());
        assertTrue( isExists );
    }

    @DisplayName(value = "Test de findByCode del ForgotEntity")
    @Test
    public void testForgotFindByCode(){
        var forgot2 = repository.findByCode(entity.getCode());
        assertTrue( forgot2.isPresent() );
        assertThat(forgot2.get().getId()).isEqualTo(user.getId());
    }

    @DisplayName(value = "Test de findByEmail del ForgotEntity")
    @Test
    public void testFindByEmail(){
        var forgot2 = repository.findByUserEntityEmail(user.getEmail());
        assertTrue( forgot2.isPresent() );
        assertThat(forgot2.get().getId()).isEqualTo(user.getId());
    }
}


