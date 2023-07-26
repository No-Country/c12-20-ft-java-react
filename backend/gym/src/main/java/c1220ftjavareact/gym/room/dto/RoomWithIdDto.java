package c1220ftjavareact.gym.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomWithIdDto {
    private Long id;
    private String name;
    private int maxCapacity;
}
