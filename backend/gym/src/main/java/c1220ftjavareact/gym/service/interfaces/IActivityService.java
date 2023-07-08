package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.ActivityInDto;
import c1220ftjavareact.gym.repository.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface IActivityService {

    public Activity createActivity(ActivityInDto activityInDto);

    public void deleteActivity(Long id);

    public Activity updateActivity(Long id, ActivityInDto activityInDto);

    public Activity getActivityById(Long id);

    public List<Activity> getAllActivities();
}
