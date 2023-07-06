package c1220ftjavareact.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private int id;

    private int idSubscription;

    private LocalDateTime day;

    private LocalDateTime expired;


}
