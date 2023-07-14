package c1220ftjavareact.gym.room.entity;

import c1220ftjavareact.gym.training.entity.TrainingSession;
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
    private int maxCapacity;

    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<TrainingSession> trainingSession;

}
