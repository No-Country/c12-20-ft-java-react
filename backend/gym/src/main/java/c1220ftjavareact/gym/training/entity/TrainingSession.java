package c1220ftjavareact.gym.training.entity;

import c1220ftjavareact.gym.activity.entity.Activity;
import c1220ftjavareact.gym.room.entity.Room;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import c1220ftjavareact.gym.subscription.model.Subscription;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;


@Entity
@Builder
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

    @OneToMany
    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "training_session_id", referencedColumnName = "id")
    private List<SubscriptionEntity> subscription;


}
