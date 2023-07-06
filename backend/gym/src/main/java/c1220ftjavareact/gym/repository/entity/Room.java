package c1220ftjavareact.gym.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "max_capacity", nullable = false)
    private int max_capacity;

    @JoinColumn(name = "id_training_session", referencedColumnName = "id")
    @OneToMany
    private List<TrainingSession> trainingSession;
}
