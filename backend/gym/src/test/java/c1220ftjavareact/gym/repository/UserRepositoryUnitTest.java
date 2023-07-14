package c1220ftjavareact.gym.repository;

import c1220ftjavareact.gym.user.enums.Role;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryUnitTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    private UserEntity entity;

    @BeforeEach
    public void setup(){
        entity= UserEntity.builder()
                .name("Junito")
                .lastname("Remirez")
                .email("jr123@gmail.com")
                .password("1234567")
                .role(Role.CUSTOMER)
                .picture("default")
                .createAt(LocalDate.now())
                .deleted(false)
                .build();
    }


    @DisplayName(value = "Test de guardar un usuario")
    @Test
    protected void testJpaSaveUser(){
        //When
        var user =userRepository.save(entity);
        //Then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isPositive();
    }

    @DisplayName(value = "Test de exists by email")
    @Test
    protected void testExistsByEmail(){
        //Debe de ser falso
        assertFalse( userRepository.existsByEmail(entity.getEmail()) );
        //Guardamos
        userRepository.save(entity);
        //Debe de ser true al guardar
        assertTrue( userRepository.existsByEmail(entity.getEmail()) );
    }

    @DisplayName(value = "Test de exists by Id")
    @Test
    protected void testExistsById(){
        //When
        var user =userRepository.save(entity);
        //Then
        assertThat(user).isNotNull();
        assertTrue( userRepository.existsById(user.getId()) );
    }

    @DisplayName(value = "Test de UserSave Query Method")
    @Test
    protected void testSaveUser(){
        //Then
        assertDoesNotThrow(()->
                //When
                userRepository.saveUser(entity.getName(), entity.getEmail(), entity.getLastname(), entity.getPassword(), entity.getRole().name())
        );
    }

    @DisplayName(value = "Test de find User for login")
    @Test
    protected void testFindUserForLogin(){
        //Given
        userRepository.save(entity);
        //When
        var user =userRepository.findUserForLogin(entity.getEmail());
        //Then
        assertThat(user).isNotNull();
        assertThat(Long.parseLong(user.getId())).isPositive();
        assertThat(user.getEmail()).isNotBlank();
        assertThat(user.getFullName()).isNotBlank();
    }

    @DisplayName(value = "Test de Delete User")
    @Test
    protected void testDeleteUser(){
        //Given
        var user =userRepository.save(entity);
        assertTrue( userRepository.existsById(user.getId()) );
        //When
        assertDoesNotThrow(()->
                userRepository.deleteUsersBy(user.getId().toString(), user.getRole().name())
        );

        //Then
        assertFalse( userRepository.existsById(user.getId()) );
    }


    @DisplayName(value = "Test de Change State User")
    @Test
    protected void testChangeState(){
        //Given
        var user =userRepository.save(entity);
        assertFalse(user.getDeleted());
        //When
        userRepository.changeStateUser(user.getId().toString(), user.getRole().name(), user.getDeleted() ? "0" : "1");
        entityManager.clear();
        //Then
        var user2 = userRepository.findById(user.getId());
        assertTrue( user2.isPresent() );
        assertTrue( user2.get().getDeleted() );
    }

    @DisplayName(value = "Test de find By Email")
    @Test
    protected void testFindByEmail(){
        //Given
        userRepository.save(entity);
        //When
        var user2 = userRepository.findByEmail(entity.getEmail());
        //Then
        assertTrue( user2.isPresent() );
    }

    @DisplayName(value = "Test de count user by id and role")
    @Test
    protected void testCountUserByIdAndRole(){
        //Given
        var user =userRepository.save(entity);
        //When
        var count = userRepository.countUsersBy(user.getId().toString(), user.getRole().name());
        //Then
        assertThat( count ).isPositive();
        assertThat( count ).isEqualTo(1);

    }

    @DisplayName(value = "Test de count admins")
    @Test
    protected void testCountAdmins(){
        Integer count = userRepository.countAdmins();
        assertThat(count).isNotNull();
    }
}
