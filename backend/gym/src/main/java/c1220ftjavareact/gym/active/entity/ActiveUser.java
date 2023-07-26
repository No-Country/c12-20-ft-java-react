package c1220ftjavareact.gym.active.entity;

import c1220ftjavareact.gym.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "active_user")
public class ActiveUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private Role role;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    private String picture;

    private String token;

}
