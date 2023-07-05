package c1220ftjavareact.gym.domain;


import c1220ftjavareact.gym.repository.entity.State;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    private int id;
    private int idClient;
    private int idClass;
    private State state;
    private LocalDateTime subscriptionDay;

}

