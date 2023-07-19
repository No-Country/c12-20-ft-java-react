package c1220ftjavareact.gym.room.entity;

import c1220ftjavareact.gym.training.entity.TrainingSession;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "name")
    private String name;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @JsonBackReference
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @OneToMany
    private List<TrainingSession> trainingSession;

    @Column(name = "deleted")
    private boolean deleted;

}
