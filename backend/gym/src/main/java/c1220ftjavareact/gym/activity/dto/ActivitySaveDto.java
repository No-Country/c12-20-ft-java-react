package c1220ftjavareact.gym.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySaveDto {

    private String name;

    private String description;

    private String img;
}
