package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.ActivityDto;
import c1220ftjavareact.gym.repository.entity.Activity;

import java.util.List;

public interface IActivityService {

    public ActivityDto createActivity(ActivityDto activityDto);

    public void deleteActivity(Long id);

    public ActivityDto updateActivity(Long id, ActivityDto activityDto);

    public ActivityDto getActivitDtoyById(Long id);

    public List<ActivityDto> getAllActivities();

    public Activity getActivityById(Long id);
}
