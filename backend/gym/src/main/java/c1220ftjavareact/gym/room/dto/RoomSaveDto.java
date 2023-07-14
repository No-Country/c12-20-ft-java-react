package c1220ftjavareact.gym.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomSaveDto {
    private String name;
    private int maxCapacity;
}
