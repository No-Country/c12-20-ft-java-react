package c1220ftjavareact.gym.repository.entity;

import c1220ftjavareact.gym.domain.dto.UserUpdateDTO;
import c1220ftjavareact.gym.domain.exception.UserSaveException;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "enum('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    private Role role;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "picture")
    private String picture;

    public UserEntity(String email, String password, String name, String lastname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }

    public String fullname() {
        return new StringBuilder()
                .append(this.getName())
                .append(" ")
                .append(this.getLastname())
                .toString();
    }

    public void update(UserUpdateDTO dto, PasswordEncoder encoder){
        this.setName(StringUtils.hasText(dto.name()) ? dto.name() : this.getName());
        this.setLastname(StringUtils.hasText(dto.lastName()) ? dto.lastName() : this.getLastname());
        this.setEmail(StringUtils.hasText(dto.email()) ? dto.email() : this.getEmail());
        this.setPicture(StringUtils.hasText(dto.picture()) ? dto.picture() : this.getPicture());

        if( StringUtils.hasText(dto.updatedPassword()) ){
            if(!encoder.matches(dto.oldPassword(), this.getPassword())){
                throw new UserSaveException("La contraseña antigua es incorrecta", "Poner la contraseña registrada, o no poner los datos de cambio de contraseña");
            }
            this.setPassword(encoder.encode(dto.updatedPassword()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role != null
                ? List.of(new SimpleGrantedAuthority(this.getRole().name()))
                : null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.getDeleted();
    }


}
