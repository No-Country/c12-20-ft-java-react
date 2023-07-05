package c1220ftjavareact.gym.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;


@Entity
@Getter
@Setter
@Table(name = "training_session")
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;
    private LocalTime time_start;
    private LocalTime time_finish;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "room_id")
    private Room room;


}
