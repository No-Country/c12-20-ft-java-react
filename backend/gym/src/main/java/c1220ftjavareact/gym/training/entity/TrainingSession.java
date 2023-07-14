package c1220ftjavareact.gym.training.entity;

import c1220ftjavareact.gym.activity.entity.Activity;
import c1220ftjavareact.gym.room.entity.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;


@Entity
@Data
@Table(name = "training_session")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;
    @Column(columnDefinition = "time(0)")
    private LocalTime timeStart;
    @Column(columnDefinition = "time(0)")
    private LocalTime timeEnd;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "room_id")
    private Room room;

    /// descomentar cuando este la relacion con subscription
    /*
    @JoinColumn(name = "id_training_session", referencedColumnName = "id")
    @OneToMany
    private List<Subscription> subscriptions;
    */

}
