package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.dto.ActivityInDto;
import c1220ftjavareact.gym.repository.entity.Activity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ImpRoomInDtoToActivity implements IMapperActivity<ActivityInDto, Activity> {
    @Override
    public Activity map(ActivityInDto in) {
        var activity = new Activity();
        activity.setName(in.getName());
        activity.setCreateDate(LocalDate.now());
        activity.setDescription(in.getDescription());
        activity.setImg("Imagen");
        return activity;
    }
}
