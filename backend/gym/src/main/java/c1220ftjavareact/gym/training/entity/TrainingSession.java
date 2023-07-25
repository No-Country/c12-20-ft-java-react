package c1220ftjavareact.gym.training.entity;

import c1220ftjavareact.gym.activity.entity.Activity;
import c1220ftjavareact.gym.room.entity.Room;
import c1220ftjavareact.gym.subscription.entity.Subscription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "room_id")
    private Room room;

    @JoinColumn(name = "training_session_id", referencedColumnName = "id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Subscription> subscriptions;


}
