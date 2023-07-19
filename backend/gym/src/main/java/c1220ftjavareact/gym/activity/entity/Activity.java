package c1220ftjavareact.gym.activity.entity;

import c1220ftjavareact.gym.training.entity.TrainingSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity")
public class Activity implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "create_day", nullable = false)
    private LocalDate createDate;

    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    @OneToMany
    private List<TrainingSession> trainingSession;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "img", nullable = false)
    private String img;


<<<<<<< HEAD

=======
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
}
