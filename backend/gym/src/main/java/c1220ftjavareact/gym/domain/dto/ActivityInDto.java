package c1220ftjavareact.gym.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityInDto {
    private String name;
    private String description;
    private String img;
}
