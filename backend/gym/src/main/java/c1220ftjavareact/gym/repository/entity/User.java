package c1220ftjavareact.gym.repository.entity;

import c1220ftjavareact.gym.domain.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class User implements UserDetails {

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

    @Column(name = "create_day", nullable = false)
    private LocalDate createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "enum('CLIENT', 'USER', 'ADMIN')")
    private Role role;

    public User(String email, String password, String name, String lastname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }

    public String fullname() {
        var fullname = new StringBuilder();
        fullname
                .append(this.getName().substring(0, 1).toUpperCase())
                .append(this.getName().substring(1))
                .append(" ")
                .append(this.getLastname().substring(0, 1).toUpperCase())
                .append(this.getName().substring(1));
        return fullname.toString();

    }

    public void changeRole(Role role) {
        this.role = role;
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
        return true;
    }


}
